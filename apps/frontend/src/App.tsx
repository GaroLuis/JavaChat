import {useState} from 'react'
import AuthModal from './components/AuthModal.tsx'
import AsideChats from "./components/AsideChats.tsx";
import ChatHeader from "./components/ChatHeader.tsx";
import ChatBubble from "./components/ChatBubble.tsx";
import ChatInput from "./components/ChatInput.tsx";

export interface Message {
  id: string;
  content: string;
  timestamp: number
  sender: User
}

export interface User {
  id: string;
  username: string;
  connected: number
  last_connection: number
}

export interface Room {
  id: string
  user: User
  messages: Message[]
}

function App() {
  const [selectedRoom, setSelectedRoom] = useState<Room>()

  const me: User | null = null

  return (
    <div className="flex h-svh max-h-svh overflow-hidden">
      {null === me ? (
        <AuthModal/>
      ) : (
        <>
          <AsideChats selectedRoom={selectedRoom} setSelectedRoom={setSelectedRoom}/>
          <main className="flex-1 flex flex-col min-w-0">
            {selectedRoom ? (
              <>
                <ChatHeader name={selectedRoom.user.username}/>
                <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col gap-2 bg-bg">
                  {selectedRoom.messages.map((msg) => (
                    <div
                      key={msg.id}
                      className={`flex ${msg.sender.id === me.id ? 'justify-end' : 'justify-start'}`}
                    >
                      <ChatBubble message={msg} user={me}/>
                    </div>
                  ))}
                </div>
                <ChatInput/>
              </>
            ) : (
              <div className="flex-1 flex items-center justify-center text-text text-base">
                <p>Select a conversation to start chatting</p>
              </div>
            )}
          </main>
        </>
      )}
    </div>
  )
}

export default App
