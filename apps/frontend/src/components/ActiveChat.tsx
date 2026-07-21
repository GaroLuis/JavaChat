import ChatHeader from "./ChatHeader.tsx";
import type {Room} from "../api/types/Room.ts";
import ChatBubble from "./ChatBubble.tsx";
import ChatInput from "./ChatInput.tsx";
import type {User} from "../api/types/User.ts";
import {useGetRoomMessages} from "../hooks/useGetRoomMessages.ts";

const ActiveChat = ({room, me}: ActiveChatProps) => {
  const user = room.users.find((u) => u.id !== me.id);

  const messagesQuery = useGetRoomMessages(room)
  const messages = messagesQuery.data?.data ?? []

  return (
    <>
      <ChatHeader name={user!.username}/>
      <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col gap-2 bg-bg">
        {messages.map((msg) => (
          <div
            key={msg.id}
            className={`flex ${msg.sender.id === me.id ? 'justify-end' : 'justify-start'}`}
          >
            <ChatBubble message={msg} user={me}/>
          </div>
        ))}
      </div>
      <ChatInput roomId={room.id}/>
    </>
  )
}

export default ActiveChat

interface ActiveChatProps {
  room: Room;
  me: User;
}
