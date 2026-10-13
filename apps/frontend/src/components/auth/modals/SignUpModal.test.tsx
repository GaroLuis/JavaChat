import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import SignUpModal from './SignUpModal'
import { AllProviders } from '../../../test/test-utils'

vi.mock('../../../api/repositories/users', () => ({
  createUser: vi.fn(),
}))

vi.mock('../../../api/repositories/auth', () => ({
  login: vi.fn(),
}))

import { createUser } from '../../../api/repositories/users'
import { login } from '../../../api/repositories/auth'

const mockedCreateUser = vi.mocked(createUser)
const mockedLogin = vi.mocked(login)

const createTestQueryClient = () =>
  new QueryClient({
    defaultOptions: {
      queries: { retry: false },
      mutations: { retry: false },
    },
  })

const renderWithClient = (ui: React.ReactElement) => {
  const queryClient = createTestQueryClient()
  return render(
    <QueryClientProvider client={queryClient}>
      <AllProviders>{ui}</AllProviders>
    </QueryClientProvider>,
  )
}

describe('SignUpModal', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders sign up heading and description', () => {
    renderWithClient(<SignUpModal onClose={vi.fn()} />)
    expect(screen.getByRole('heading', { name: /sign up/i })).toBeInTheDocument()
    expect(screen.getByText(/create your account/i)).toBeInTheDocument()
  })

  it('renders username and password inputs', () => {
    renderWithClient(<SignUpModal onClose={vi.fn()} />)
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument()
  })

  it('creates user then logs in on submit', async () => {
    mockedCreateUser.mockResolvedValueOnce({ data: {} } as never)
    mockedLogin.mockResolvedValueOnce({ data: {} } as never)
    Object.defineProperty(window, 'location', {
      value: { reload: vi.fn() },
      writable: true,
    })

    const user = userEvent.setup()
    renderWithClient(<SignUpModal onClose={vi.fn()} />)

    await user.type(screen.getByPlaceholderText('Username'), 'newuser')
    await user.type(screen.getByPlaceholderText('Password'), 'secret')
    await user.click(screen.getByRole('button', { name: /sign up/i }))

    expect(mockedCreateUser).toHaveBeenCalledWith({ username: 'newuser', password: 'secret' })
    expect(mockedLogin).toHaveBeenCalledWith({ username: 'newuser', password: 'secret' })
  })

  it('shows error and does not log in when create user fails', async () => {
    mockedCreateUser.mockRejectedValueOnce(new Error('conflict'))

    const user = userEvent.setup()
    renderWithClient(<SignUpModal onClose={vi.fn()} />)

    await user.type(screen.getByPlaceholderText('Username'), 'taken')
    await user.type(screen.getByPlaceholderText('Password'), 'secret')
    await user.click(screen.getByRole('button', { name: /sign up/i }))

    expect(await screen.findByRole('alert')).toHaveTextContent(/could not create the account/i)
    expect(mockedLogin).not.toHaveBeenCalled()
  })

  it('calls onClose when close button is clicked', async () => {
    const onClose = vi.fn()
    const user = userEvent.setup()
    renderWithClient(<SignUpModal onClose={onClose} />)

    await user.click(screen.getByRole('button', { name: /close/i }))

    expect(onClose).toHaveBeenCalled()
  })
})
