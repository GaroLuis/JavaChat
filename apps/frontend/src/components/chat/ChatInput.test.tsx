import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import ChatInput from './ChatInput'
import { WsClientContext } from '../../contexts/WsClientProvider'
import { AllProviders } from '../../test/test-utils'

const mockSendMessage = vi.fn()

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <WsClientContext.Provider value={{ sendMessage: mockSendMessage, client: null }}>
    <AllProviders>{children}</AllProviders>
  </WsClientContext.Provider>
)

describe('ChatInput', () => {
  beforeEach(() => {
    mockSendMessage.mockClear()
  })

  it('renders input and send button', () => {
    render(<ChatInput roomId="room-1" />, { wrapper })
    expect(screen.getByPlaceholderText('Type a message...')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: /send/i })).toBeInTheDocument()
  })

  it('disables send button when input is empty', () => {
    render(<ChatInput roomId="room-1" />, { wrapper })
    expect(screen.getByRole('button', { name: /send/i })).toBeDisabled()
  })

  it('enables send button when input has text', async () => {
    const user = userEvent.setup()
    render(<ChatInput roomId="room-1" />, { wrapper })

    await user.type(screen.getByPlaceholderText('Type a message...'), 'Hello')

    expect(screen.getByRole('button', { name: /send/i })).toBeEnabled()
  })

  it('calls sendMessage with correct data on submit', async () => {
    const user = userEvent.setup()
    render(<ChatInput roomId="room-1" />, { wrapper })

    const input = screen.getByPlaceholderText('Type a message...')
    await user.type(input, 'Hello there')
    await user.click(screen.getByRole('button', { name: /send/i }))

    expect(mockSendMessage).toHaveBeenCalledWith({
      content: 'Hello there',
      roomId: 'room-1',
    })
  })

  it('clears input after sending', async () => {
    const user = userEvent.setup()
    render(<ChatInput roomId="room-1" />, { wrapper })

    const input = screen.getByPlaceholderText('Type a message...')
    await user.type(input, 'Hello there')
    await user.click(screen.getByRole('button', { name: /send/i }))

    expect(input).toHaveValue('')
  })
})
