import { render, screen } from '@testing-library/react'
import { describe, it, expect, vi } from 'vitest'
import ChatBubble from './ChatBubble'
import type { User } from '../../api/types/User'
import type { Message } from '../../api/types/Message'

vi.mock('../../utils/helpers', () => ({
  formatTimestamp: () => 'Jan 1, 12:00 PM',
}))

const currentUser: User = {
  id: 'user-1',
  username: 'alice',
  connected: 1,
  lastConnection: '',
}

const otherUser: User = {
  id: 'user-2',
  username: 'bob',
  connected: 1,
  lastConnection: '',
}

const room = { id: 'room-1', users: [currentUser, otherUser] }

const makeMessage = (overrides: Partial<Message> = {}): Message => ({
  id: 'msg-1',
  content: 'Hello world',
  timestamp: '2025-01-01T12:00:00Z',
  sender: currentUser,
  room,
  ...overrides,
})

describe('ChatBubble', () => {
  it('renders message content', () => {
    render(<ChatBubble message={makeMessage()} user={currentUser} />)
    expect(screen.getByText('Hello world')).toBeInTheDocument()
  })

  it('renders formatted timestamp', () => {
    render(<ChatBubble message={makeMessage()} user={currentUser} />)
    expect(screen.getByText('Jan 1, 12:00 PM')).toBeInTheDocument()
  })

  it('applies own-message styles when sender is current user', () => {
    const { container } = render(
      <ChatBubble message={makeMessage({ sender: currentUser })} user={currentUser} />,
    )
    const bubble = container.firstChild as HTMLElement
    expect(bubble.className).toContain('bg-accent')
    expect(bubble.className).toContain('text-white')
    expect(bubble.className).toContain('rounded-br')
  })

  it('applies other-message styles when sender is different user', () => {
    const { container } = render(
      <ChatBubble message={makeMessage({ sender: otherUser })} user={currentUser} />,
    )
    const bubble = container.firstChild as HTMLElement
    expect(bubble.className).toContain('bg-code-bg')
    expect(bubble.className).toContain('text-text-h')
    expect(bubble.className).toContain('rounded-bl')
  })
})
