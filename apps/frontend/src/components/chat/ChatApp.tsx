import {useGetRooms} from "../../hooks/useGetRooms.ts";
import AsideChats from "./AsideChats.tsx";
import {useState} from "react";
import ActiveChat from "./ActiveChat.tsx";
import type {User} from "../../api/types/User.ts";
import {useTranslation} from "react-i18next";

const ChatApp = ({me}: Props) => {
  const [selectedRoomId, setSelectedRoomId] = useState<string>()
  const roomsQuery = useGetRooms()
  const rooms = roomsQuery.data?.data ?? [];
  const { t } = useTranslation()

  const selectedRoom = rooms.find((room) => room.id === selectedRoomId)

  return (
    <>
      <AsideChats selectedRoomId={selectedRoomId} setSelectedRoomId={setSelectedRoomId} me={me}/>
      <main className="flex-1 flex flex-col min-w-0">
        {selectedRoom ? (
          <ActiveChat room={selectedRoom} me={me}/>
        ) : (
          <div className="flex-1 flex items-center justify-center text-text text-base">
            <p>{t('selectConversation')}</p>
          </div>
        )}
      </main>
    </>
  )
}

export default ChatApp;

interface Props {
  me: User
}
