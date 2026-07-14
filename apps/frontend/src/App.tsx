import { useState, useEffect, useRef } from 'react'

interface Message {
  id: string
  content: string
  sender: string
  timestamp: string
}

interface Conversation {
  id: string
  name: string
  lastMessage: string
  timestamp: string
  messages: Message[]
}

const mockConversations: Conversation[] = [
  {
    id: '1',
    name: 'Alice',
    lastMessage: 'Hey, how are you?',
    timestamp: '10:30',
    messages: [
      { id: 'm1', content: 'Hey, how are you?', sender: 'Alice', timestamp: '10:30' },
      { id: 'm2', content: 'I\'m good, thanks!', sender: 'me', timestamp: '10:31' },
    ],
  },
  {
    id: '2',
    name: 'Bob',
    lastMessage: 'See you tomorrow',
    timestamp: 'Yesterday',
    messages: [
      { id: 'm3', content: 'See you tomorrow', sender: 'Bob', timestamp: 'Yesterday' },
    ],
  },
]

function App() {
  const [search, setSearch] = useState('')
  const [selected, setSelected] = useState<Conversation>(mockConversations[0])
  const [input, setInput] = useState('')
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [showModal, setShowModal] = useState(true)
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const usernameRef = useRef<HTMLInputElement>(null)

  const filtered = mockConversations.filter((c) =>
    c.name.toLowerCase().includes(search.toLowerCase())
  )

  useEffect(() => {
    if (showModal) {
      setTimeout(() => usernameRef.current?.focus(), 100)
    }
  }, [showModal])

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (username === 'admin' && password === 'password') {
      setIsAuthenticated(true)
      setShowModal(false)
      setError('')
      setUsername('')
      setPassword('')
    } else {
      setError('Invalid username or password')
    }
  }

  return (
    <div className="flex h-svh max-h-svh overflow-hidden">
      {showModal ? (
        <div className="flex-1 flex items-center justify-center bg-[var(--color-bg)]">
          <form
            onSubmit={handleSubmit}
            className="bg-[var(--color-bg)] rounded-2xl shadow-xl w-full max-w-sm mx-4 p-6"
          >
            <h2
              className="text-xl font-semibold text-[var(--color-text-h)] mb-1"
              style={{ fontFamily: 'var(--font-heading)' }}
            >
              Sign in
            </h2>
            <p className="text-sm text-[var(--color-text)] mb-5">
              This chat is locked. Enter your credentials to continue.
            </p>
            <input
              ref={usernameRef}
              type="text"
              value={username}
              onChange={(e) => {
                setUsername(e.target.value)
                setError('')
              }}
              placeholder="Username"
              autoComplete="username"
              className="w-full px-4 py-2.5 border border-[var(--color-border)] rounded-lg text-sm bg-[var(--color-code-bg)] text-[var(--color-text-h)] outline-none focus:border-[var(--color-accent)] placeholder:text-[var(--color-text)] transition-colors duration-200 mb-3"
            />
            <input
              type="password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value)
                setError('')
              }}
              placeholder="Password"
              autoComplete="current-password"
              className="w-full px-4 py-2.5 border border-[var(--color-border)] rounded-lg text-sm bg-[var(--color-code-bg)] text-[var(--color-text-h)] outline-none focus:border-[var(--color-accent)] placeholder:text-[var(--color-text)] transition-colors duration-200 mb-3"
            />
            {error && (
              <p className="text-sm text-red-500 mb-3">{error}</p>
            )}
            <button
              type="submit"
              className="w-full py-2.5 border-none rounded-lg bg-[var(--color-accent)] text-white text-sm font-semibold cursor-pointer hover:opacity-90 transition-opacity duration-200"
            >
              Sign in
            </button>
          </form>
        </div>
      ) : (<>
        <aside className="w-80 border-r border-[var(--color-border)] flex flex-col bg-[var(--color-bg)] shrink-0">
        <div className="p-5 border-b border-[var(--color-border)] flex items-center justify-between">
          <h1
            className="m-0 text-2xl font-semibold text-[var(--color-text-h)]"
            style={{ fontFamily: 'var(--font-heading)' }}
          >
            Chats
          </h1>
          <button
            onClick={() => {
              if (isAuthenticated) {
                setIsAuthenticated(false)
                setShowModal(true)
                setUsername('')
                setPassword('')
                setError('')
              } else {
                setShowModal(true)
                setUsername('')
                setPassword('')
                setError('')
              }
            }}
            className="size-9 flex items-center justify-center border border-[var(--color-border)] rounded-lg bg-transparent cursor-pointer text-[var(--color-text)] hover:text-[var(--color-text-h)] hover:bg-[var(--color-code-bg)] transition-colors duration-150"
            title={isAuthenticated ? 'Lock chat' : 'Unlock chat'}
          >
            {isAuthenticated ? (
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-4">
                <path fillRule="evenodd" d="M12 1.5a5.25 5.25 0 00-5.25 5.25v3a3 3 0 00-3 3v6.75a3 3 0 003 3h10.5a3 3 0 003-3v-6.75a3 3 0 00-3-3v-3c0-2.9-2.35-5.25-5.25-5.25zm3.75 8.25v-3a3.75 3.75 0 10-7.5 0v3h7.5z" clipRule="evenodd" />
              </svg>
            ) : (
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-4">
                <path d="M18 1.5c2.9 0 5.25 2.35 5.25 5.25v3.75a.75.75 0 01-1.5 0V6.75a3.75 3.75 0 10-7.5 0v3a3 3 0 013 3v6.75a3 3 0 01-3 3H3.75a3 3 0 01-3-3v-6.75a3 3 0 013-3h9v-3c0-2.9 2.35-5.25 5.25-5.25z" />
              </svg>
            )}
          </button>
        </div>
        <div className="px-5 py-3">
          <input
            className="w-full px-3.5 py-2.5 border border-[var(--color-border)] rounded-lg text-sm bg-[var(--color-code-bg)] text-[var(--color-text-h)] outline-none focus:border-[var(--color-accent)] placeholder:text-[var(--color-text)] transition-colors duration-200"
            type="text"
            placeholder="Search users..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>
        <nav className="flex-1 overflow-y-auto py-1">
          {filtered.map((conv) => (
            <button
              key={conv.id}
              className={`flex items-center gap-3 w-full px-5 py-3 border-none bg-transparent cursor-pointer text-left text-[var(--color-text)] hover:bg-[var(--color-code-bg)] transition-colors duration-150 ${selected.id === conv.id ? 'bg-[var(--color-accent-bg)]' : ''}`}
              onClick={() => setSelected(conv)}
            >
              <div className="size-11 rounded-full bg-[var(--color-accent)] text-white flex items-center justify-center font-semibold text-base shrink-0">
                {conv.name[0]}
              </div>
              <div className="flex-1 min-w-0">
                <div className="text-[15px] font-semibold text-[var(--color-text-h)] mb-0.5">
                  {conv.name}
                </div>
                <div className="text-[13px] text-[var(--color-text)] truncate">
                  {conv.lastMessage}
                </div>
              </div>
              <div className="text-xs text-[var(--color-text)] shrink-0">
                {conv.timestamp}
              </div>
            </button>
          ))}
        </nav>
      </aside>
      <main className="flex-1 flex flex-col min-w-0">
        {selected ? (
          <>
            <header className="flex items-center gap-3 px-6 py-4 border-b border-[var(--color-border)] bg-[var(--color-bg)]">
              <div className="size-10 rounded-full bg-[var(--color-accent)] text-white flex items-center justify-center font-semibold shrink-0">
                {selected.name[0]}
              </div>
              <div className="flex-1">
                <div className="text-base font-semibold text-[var(--color-text-h)]">
                  {selected.name}
                </div>
                <div className="text-xs text-green-500">Online</div>
              </div>
            </header>

            <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col gap-2 bg-[var(--color-bg)]">
              {selected.messages.map((msg) => (
                <div
                  key={msg.id}
                  className={`flex ${msg.sender === 'me' ? 'justify-end' : 'justify-start'}`}
                >
                  <div
                    className={`max-w-[70%] px-3.5 py-2.5 rounded-2xl text-[15px] leading-snug ${msg.sender === 'me' ? 'bg-[var(--color-accent)] text-white rounded-br' : 'bg-[var(--color-code-bg)] text-[var(--color-text-h)] rounded-bl'}`}
                  >
                    <div className="break-words">{msg.content}</div>
                    <div className="text-[11px] opacity-70 mt-1 text-right">
                      {msg.timestamp}
                    </div>
                  </div>
                </div>
              ))}
            </div>

            <div className="flex gap-2 px-6 py-4 border-t border-[var(--color-border)] bg-[var(--color-bg)]">
              <input
                className="flex-1 px-4 py-2.5 border border-[var(--color-border)] rounded-full text-[15px] bg-[var(--color-code-bg)] text-[var(--color-text-h)] outline-none focus:border-[var(--color-accent)] placeholder:text-[var(--color-text)] transition-colors duration-200"
                type="text"
                placeholder="Type a message..."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === 'Enter' && input.trim()) {
                    setInput('')
                  }
                }}
              />
              <button
                className="px-5 py-2.5 border-none rounded-full bg-[var(--color-accent)] text-white text-sm font-semibold cursor-pointer hover:opacity-90 disabled:opacity-50 disabled:cursor-not-allowed shrink-0 transition-opacity duration-200"
                disabled={!input.trim()}
                onClick={() => setInput('')}
              >
                Send
              </button>
            </div>
          </>
        ) : (
          <div className="flex-1 flex items-center justify-center text-[var(--color-text)] text-base">
            <p>Select a conversation to start chatting</p>
          </div>
        )}
      </main>
      </>)}
    </div>
  )
}

export default App
