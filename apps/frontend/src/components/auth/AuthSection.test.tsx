import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import AuthSection from './AuthSection.tsx'
import { AllProviders } from '../../test/test-utils.tsx'

vi.mock('../../../api/repositories/auth', () => ({
  login: vi.fn(),
}))

import { login } from '../../api/repositories/auth.ts'

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

describe('AuthSection', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders sign in heading', () => {
    renderWithClient(<AuthSection />)
    expect(screen.getByRole('heading', { name: /sign in/i })).toBeInTheDocument()
  })

  it('renders description text', () => {
    renderWithClient(<AuthSection />)
    expect(screen.getByText(/this chat is locked/i)).toBeInTheDocument()
  })

  it('renders username and password inputs', () => {
    renderWithClient(<AuthSection />)
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument()
  })

  it('renders sign in button', () => {
    renderWithClient(<AuthSection />)
    expect(screen.getByRole('button', { name: /sign in/i })).toBeInTheDocument()
  })

  it('renders sign up button below sign in form', () => {
    renderWithClient(<AuthSection />)
    expect(screen.getByRole('button', { name: /sign up/i })).toBeInTheDocument()
    expect(screen.getByText(/don't have an account/i)).toBeInTheDocument()
  })

  it('opens sign up modal when clicking sign up', async () => {
    const user = userEvent.setup()
    renderWithClient(<AuthSection />)

    await user.click(screen.getByRole('button', { name: /sign up/i }))

    expect(screen.getByText(/create your account/i)).toBeInTheDocument()
  })

  it('closes sign up modal when clicking close', async () => {
    const user = userEvent.setup()
    renderWithClient(<AuthSection />)

    await user.click(screen.getByRole('button', { name: /sign up/i }))
    await user.click(screen.getByRole('button', { name: /close/i }))

    expect(screen.queryByText(/create your account/i)).not.toBeInTheDocument()
  })

  it('calls login mutation on form submit', async () => {
    mockedLogin.mockResolvedValueOnce({ data: {} } as never)
    Object.defineProperty(window, 'location', {
      value: { reload: vi.fn() },
      writable: true,
    })

    const user = userEvent.setup()
    renderWithClient(<AuthSection />)

    await user.type(screen.getByPlaceholderText('Username'), 'admin')
    await user.type(screen.getByPlaceholderText('Password'), 'secret')
    await user.click(screen.getByRole('button', { name: /sign in/i }))

    expect(mockedLogin).toHaveBeenCalledWith(
      { username: 'admin', password: 'secret' },
      expect.anything(),
    )
  })
})
