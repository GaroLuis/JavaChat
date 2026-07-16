import {useState} from 'react'
import AuthModal from './components/AuthModal.tsx'
import AsideChats from "./components/AsideChats.tsx";
import type {Room} from "./api/types/Room.ts";
import {useQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "./api/queryKeys.ts";
import {getMe} from "./api/repositories/users.ts";
import ActiveChat from "./components/ActiveChat.tsx";

function App() {
  const [selectedRoom, setSelectedRoom] = useState<Room>()

  const meQuery = useQuery({
    queryKey: [QUERY_KEYS.ME],
    queryFn: getMe,
  })

  const me = meQuery.data?.data

  return (
    <div className="flex h-svh max-h-svh overflow-hidden">
      {null == me ? (
        <AuthModal/>
      ) : (
        <>
          <AsideChats selectedRoom={selectedRoom} setSelectedRoom={setSelectedRoom} me={me} />
          <main className="flex-1 flex flex-col min-w-0">
            {selectedRoom ? (
              <ActiveChat room={selectedRoom} me={me} />
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
