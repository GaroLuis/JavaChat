import {useMutation, useQueryClient} from "@tanstack/react-query";
import {deleteRoom} from "../../api/repositories/rooms.ts";
import {QUERY_KEYS} from "../../api/queryKeys.ts";
import type {Props as AsideProps} from "./AsideChats.tsx";
import type {Room} from "../../api/types/Room.ts";
import {formatTimestamp} from "../../utils/helpers.ts";
import {useGetRoomMessages} from "../../hooks/useGetRoomMessages.ts";

const RoomItem = ({room, me, selectedRoomId, setSelectedRoomId}: Props) => {
  const queryClient = useQueryClient()

  const deleteRoomMutation = useMutation({
    mutationFn: (roomId: string) => deleteRoom(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
    },
  })

  const messagesQuery = useGetRoomMessages(room);

  const user = room.users.find((u) => u.id !== me.id);
  const lastMessage = messagesQuery.data?.pages && messagesQuery.data.pages[0]?.length > 0
    ? messagesQuery.data.pages[0][0]
    : null;

  return (
    <div className={'flex relative'} key={room.id}>
      <button
        key={room.id}
        className={`flex items-center gap-3 w-full px-5 py-3 border-none bg-transparent cursor-pointer text-left text-text hover:bg-code-bg transition-colors duration-150 ${selectedRoomId === room.id ? 'bg-accent-bg' : ''}`}
        onClick={() => setSelectedRoomId(room.id)}
      >
        <div
          className="size-11 rounded-full bg-accent text-white flex items-center justify-center font-semibold text-base shrink-0">
          {user!.username[0].toUpperCase()}
        </div>
        <div className="flex-1 min-w-0">
          <div className="text-[15px] font-semibold text-text-h mb-0.5">
            {user!.username}
          </div>
          <div className="text-[13px] text-text truncate">
            {lastMessage ? `${lastMessage.sender.username}: ${lastMessage.content}` : null}
          </div>
        </div>
        <div className="text-xs text-text shrink-0">
          {lastMessage?.timestamp ? formatTimestamp(lastMessage.timestamp) : null}
        </div>
      </button>
      <button
        onClick={(e) => {
          e.stopPropagation()
          deleteRoomMutation.mutate(room.id)
        }}
        className="absolute size-6 flex items-center justify-center rounded hover:bg-red-500/20 text-text hover:text-red-500 transition-colors cursor-pointer border-none bg-transparent shrink-0"
        title="Delete chat"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
          <line x1="18" y1="6" x2="6" y2="18"/>
          <line x1="6" y1="6" x2="18" y2="18"/>
        </svg>
      </button>
    </div>
  )
}

export default RoomItem

interface Props extends AsideProps{
  room: Room
}
