import type {User} from "../../api/types/User.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import AsyncSelect from "react-select/async";
import {createRoom} from "../../api/repositories/rooms.ts";
import {QUERY_KEYS} from "../../api/queryKeys.ts";
import {getUsers} from "../../api/repositories/users.ts";
import {useGetRooms} from "../../hooks/useGetRooms.ts";
import RoomItem from "./RoomItem.tsx";
import {useTranslation} from "react-i18next";

interface UserOption {
  value: string;
  label: string;
}

const AsideChats = ({selectedRoomId, setSelectedRoomId, me}: Props) => {
  const queryClient = useQueryClient()
  const { t } = useTranslation()

  const createRoomMutation = useMutation({
    mutationFn: (userId: string) => createRoom([userId]),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
    },
  })

  const roomsQuery = useGetRooms()
  const rooms = roomsQuery.data?.data ?? [];

  return (
    <aside className="w-80 border-r border-border flex flex-col bg-bg shrink-0">
      <div className="p-5 border-b border-border flex items-center justify-between">
        <h1
          className="m-0 text-2xl font-semibold text-text-h"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          {t('chats')}
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
          placeholder={t('searchUsers')}
          noOptionsMessage={() => null}
          loadingMessage={() => t('searching')}
          value={null}
        />
      </div>
      <nav className="flex-1 overflow-y-auto py-1">
        {rooms.map((room) => (
            <RoomItem key={room.id} room={room} me={me} setSelectedRoomId={setSelectedRoomId} selectedRoomId={selectedRoomId}/>
          )
        )}
      </nav>
    </aside>
  )
}

export interface Props {
  selectedRoomId?: string;
  setSelectedRoomId: (roomId: string) => void;
  me: User;
}

export default AsideChats;
