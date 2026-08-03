import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import AuthModal from './AuthModal'
import { AllProviders } from '../../../test/test-utils'

vi.mock('../../../api/repositories/auth', () => ({
  login: vi.fn(),
}))

import { login } from '../../../api/repositories/auth'

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

describe('AuthModal', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders sign in heading', () => {
    renderWithClient(<AuthModal />)
    expect(screen.getByRole('heading', { name: /sign in/i })).toBeInTheDocument()
  })

  it('renders description text', () => {
    renderWithClient(<AuthModal />)
    expect(screen.getByText(/this chat is locked/i)).toBeInTheDocument()
  })

  it('renders username and password inputs', () => {
    renderWithClient(<AuthModal />)
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument()
  })

  it('renders sign in button', () => {
    renderWithClient(<AuthModal />)
    expect(screen.getByRole('button', { name: /sign in/i })).toBeInTheDocument()
  })

  it('calls login mutation on form submit', async () => {
    mockedLogin.mockResolvedValueOnce({ data: {} } as never)
    Object.defineProperty(window, 'location', {
      value: { reload: vi.fn() },
      writable: true,
    })

    const user = userEvent.setup()
    renderWithClient(<AuthModal />)

    await user.type(screen.getByPlaceholderText('Username'), 'admin')
    await user.type(screen.getByPlaceholderText('Password'), 'secret')
    await user.click(screen.getByRole('button', { name: /sign in/i }))

    expect(mockedLogin).toHaveBeenCalledWith(
      { username: 'admin', password: 'secret' },
      expect.anything(),
    )
  })
})
