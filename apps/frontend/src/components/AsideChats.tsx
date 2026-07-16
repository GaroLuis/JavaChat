import type {Room} from "../api/types/Room.ts";
import type {User} from "../api/types/User.ts";
import {useQuery, useMutation, useQueryClient} from "@tanstack/react-query";
import AsyncSelect from "react-select/async";
import {getRooms, createRoom, deleteRoom} from "../api/repositories/rooms.ts";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getUsers} from "../api/repositories/users.ts";
import {formatTimestamp} from "../utils/helpers.ts";

interface UserOption {
  value: string;
  label: string;
}

const AsideChats = ({selectedRoom, setSelectedRoom, me}: Props) => {
  const queryClient = useQueryClient()

  const createRoomMutation = useMutation({
    mutationFn: (userId: string) => createRoom([userId]),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
    },
  })

  const deleteRoomMutation = useMutation({
    mutationFn: (roomId: string) => deleteRoom(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
    },
  })

  const roomsQuery = useQuery({
    queryKey: [QUERY_KEYS.ROOMS],
    queryFn: getRooms
  })

  const rooms = roomsQuery.data?.data ?? [];

  return (
    <aside className="w-80 border-r border-border flex flex-col bg-bg shrink-0">
      <div className="p-5 border-b border-border flex items-center justify-between">
        <h1
          className="m-0 text-2xl font-semibold text-text-h"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          Chats
        </h1>
      </div>
      <div className="px-5 py-3">
        <AsyncSelect
          cacheOptions
          defaultOptions={false}
          loadOptions={async (inputValue: string): Promise<UserOption[]> => {
            if (inputValue.length < 1) {
              return []
            }

            const {data} = await getUsers(inputValue)
            return data.map((u: User) => ({value: u.id, label: u.username}))
          }}
          onChange={async (option: UserOption | null) => {
            if (!option) {
              return
            }

            await createRoomMutation.mutateAsync(option.value)
          }}
          placeholder="Search users..."
          noOptionsMessage={() => null}
          loadingMessage={() => 'Searching...'}
          value={null}
        />
      </div>
      <nav className="flex-1 overflow-y-auto py-1">
        {rooms.map((room) => {
          const user = room.users.find((u) => u.id !== me.id);
          const lastMessage = room.messages?.length > 0 ? room.messages[room.messages.length - 1] : null;

          return (
            <button
              key={room.id}
              className={`flex items-center gap-3 w-full px-5 py-3 border-none bg-transparent cursor-pointer text-left text-text hover:bg-code-bg transition-colors duration-150 ${selectedRoom?.id === room.id ? 'bg-accent-bg' : ''}`}
              onClick={() => setSelectedRoom(room)}
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
                  {lastMessage ? `${lastMessage?.sender.username}: ${lastMessage.content}` : null}
                </div>
              </div>
              <div className="text-xs text-text shrink-0">
                {lastMessage?.timestamp ? formatTimestamp(lastMessage?.timestamp) : null}
              </div>
              <button
                onClick={(e) => {
                  e.stopPropagation()
                  deleteRoomMutation.mutate(room.id)
                }}
                className="size-6 flex items-center justify-center rounded hover:bg-red-500/20 text-text hover:text-red-500 transition-colors cursor-pointer border-none bg-transparent shrink-0"
                title="Delete chat"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <line x1="18" y1="6" x2="6" y2="18"/>
                  <line x1="6" y1="6" x2="18" y2="18"/>
                </svg>
              </button>
            </button>
          )
        })}
      </nav>
    </aside>
  )
}

interface Props {
  selectedRoom?: Room;
  setSelectedRoom: (room: Room) => void;
  me: User;
}

export default AsideChats;
