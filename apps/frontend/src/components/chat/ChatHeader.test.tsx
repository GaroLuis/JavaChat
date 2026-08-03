import { render, screen } from '@testing-library/react'
import { describe, it, expect, vi } from 'vitest'
import ChatHeader from './ChatHeader'
import type { User } from '../../api/types/User'

vi.mock('../../utils/helpers', () => ({
  formatTimestamp: () => 'Jan 1, 12:00 PM',
}))

const makeUser = (overrides: Partial<User> = {}): User => ({
  id: 'user-1',
  username: 'alice',
  connected: 1,
  lastConnection: '',
  ...overrides,
})

describe('ChatHeader', () => {
  it('renders username', () => {
    render(<ChatHeader user={makeUser()} />)
    expect(screen.getByText('alice')).toBeInTheDocument()
  })

  it('renders first letter of username as avatar', () => {
    render(<ChatHeader user={makeUser()} />)
    expect(screen.getByText('A')).toBeInTheDocument()
  })

  it('shows Online status when connected', () => {
    render(<ChatHeader user={makeUser({ connected: 1 })} />)
    expect(screen.getByText('Online')).toBeInTheDocument()
  })

  it('shows Offline status when not connected', () => {
    render(<ChatHeader user={makeUser({ connected: 0 })} />)
    expect(screen.getByText('Offline')).toBeInTheDocument()
  })

  it('shows last connection time when offline and lastConnection is set', () => {
    render(
      <ChatHeader
        user={makeUser({ connected: 0, lastConnection: '2025-01-01T12:00:00Z' })}
      />,
    )
    expect(screen.getByText('Jan 1, 12:00 PM')).toBeInTheDocument()
  })

  it('does not show last connection time when online', () => {
    render(
      <ChatHeader
        user={makeUser({ connected: 1, lastConnection: '2025-01-01T12:00:00Z' })}
      />,
    )
    expect(screen.queryByText('Jan 1, 12:00 PM')).not.toBeInTheDocument()
  })
})
