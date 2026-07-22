import {useContext, useState} from 'react'
import AuthModal from './components/AuthModal.tsx'
import AsideChats from "./components/AsideChats.tsx";
import type {Room} from "./api/types/Room.ts";
import ActiveChat from "./components/ActiveChat.tsx";
import {useGetMe} from "./hooks/useGetMe.ts";
import LoadingBar from "./components/LoadingBar.tsx";
import {WsClientContext} from "./contexts/WsClientProvider.tsx";

function App() {
  return (
    <div className="flex h-svh max-h-svh overflow-hidden">
      <Content/>
    </div>
  )
}

const Content = () => {
  const [selectedRoom, setSelectedRoom] = useState<Room>()
  const {client} = useContext(WsClientContext)

  const meQuery = useGetMe()
  const me = meQuery.data?.data

  if (meQuery.isLoading) {
    return (
      <LoadingBar/>
    )
  }

  if (null == me || !client?.active) {
    return (
      <AuthModal/>
    )
  }

  return (
    <>
      <AsideChats selectedRoom={selectedRoom} setSelectedRoom={setSelectedRoom} me={me}/>
      <main className="flex-1 flex flex-col min-w-0">
        {selectedRoom ? (
          <ActiveChat room={selectedRoom} me={me}/>
        ) : (
          <div className="flex-1 flex items-center justify-center text-text text-base">
            <p>Select a conversation to start chatting</p>
          </div>
        )}
      </main>
    </>
  )
}

export default App
