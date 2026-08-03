import {useRef} from "react";
import ChatHeader from "./ChatHeader.tsx";
import type {Room} from "../../api/types/Room.ts";
import ChatBubble from "./ChatBubble.tsx";
import ChatInput from "./ChatInput.tsx";
import type {User} from "../../api/types/User.ts";
import {useGetRoomMessages} from "../../hooks/useGetRoomMessages.ts";
import {useTranslation} from "react-i18next";

const ActiveChat = ({room, me}: ActiveChatProps) => {
  const user = room.users.find((u) => u.id !== me.id);
  const scrollContainerRef = useRef<HTMLDivElement>(null);
  const { t } = useTranslation()

  const messagesQuery = useGetRoomMessages(room);
  const messages = messagesQuery.data?.pages.flatMap((page) => page) ?? [];

  return (
    <>
      <ChatHeader user={user!}/>
      <div
        ref={scrollContainerRef}
        onScroll={() => {
          const el = scrollContainerRef.current;
          if (!el) {
            return;
          }

          if (el.scrollTop === 0 && messagesQuery.hasNextPage && !messagesQuery.isFetchingNextPage) {
            const previousScrollHeight = el.scrollHeight;

            messagesQuery.fetchNextPage().then(() => {
              requestAnimationFrame(() => {
                if (scrollContainerRef.current) {
                  const newScrollHeight = scrollContainerRef.current.scrollHeight;
                  scrollContainerRef.current.scrollTop = newScrollHeight - previousScrollHeight;
                }
              });
            });
          }
        }}
        className="flex-1 overflow-y-auto px-6 py-5 flex flex-col gap-2 bg-bg"
      >
        {messagesQuery.hasNextPage && (
          <div className="text-center text-text py-2 text-sm">
            {messagesQuery.isFetchingNextPage ? t('loadingOlderMessages') : t('scrollUpOlderMessages')}
          </div>
        )}
        {messages.toReversed().map((msg) => (
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
