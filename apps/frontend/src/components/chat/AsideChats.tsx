import type {User} from "../../api/types/User.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import AsyncSelect from "react-select/async";
import {createRoom} from "../../api/repositories/rooms.ts";
import {QUERY_KEYS} from "../../api/queryKeys.ts";
import {getContacts} from "../../api/repositories/users.ts";
import {useGetRooms} from "../../hooks/useGetRooms.ts";
import RoomItem from "./RoomItem.tsx";
import {useTranslation} from "react-i18next";
import {useEffect, useMemo, useState} from "react";
import EditUserModal from "./modals/EditUserModal.tsx";
import ContactsModal from "./modals/ContactsModal.tsx";
import debounce from "lodash/debounce";

interface UserOption {
  value: string;
  label: string;
}

const AsideChats = ({selectedRoomId, setSelectedRoomId, me}: Props) => {
  const queryClient = useQueryClient()
  const { t } = useTranslation()
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const [showEditUserModal, setShowEditUserModal] = useState(false)
  const [showContactsModal, setShowContactsModal] = useState(false)

  const createRoomMutation = useMutation({
    mutationFn: (userId: string) => createRoom([userId]),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
    },
  })

  const roomsQuery = useGetRooms()
  const rooms = roomsQuery.data?.data ?? [];

  const loadContacts = useMemo(() => {
    let latestRequest = 0

    return debounce((inputValue: string, callback: (options: UserOption[]) => void) => {
      const requestId = ++latestRequest

      getContacts()
        .then(({data}) => {
          if (requestId !== latestRequest) {
            return
          }

          const query = inputValue.toLowerCase()
          callback(
            data
              .filter((u: User) => u.username.toLowerCase().includes(query))
              .map((u: User) => ({value: u.id, label: u.username}))
          )
        })
        .catch(() => {
          if (requestId === latestRequest) {
            callback([])
          }
        })
    }, 300)
  }, [])

  useEffect(() => () => loadContacts.cancel(), [loadContacts])

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
          loadOptions={loadContacts}
          onChange={async (option: UserOption | null) => {
            if (!option) {
              return
            }

            await createRoomMutation.mutateAsync(option.value)
          }}
          placeholder={t('searchContacts')}
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
      <div className="p-3 border-t border-border">
        <div className="relative">
          <button
            onClick={() => setIsMenuOpen((open) => !open)}
            aria-label={t('settings')}
            aria-haspopup="menu"
            aria-expanded={isMenuOpen}
            className="size-8 flex items-center justify-center rounded hover:bg-code-bg text-text hover:text-text-h transition-colors duration-150 cursor-pointer border-none bg-transparent"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <circle cx="12" cy="12" r="3"/>
              <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/>
            </svg>
          </button>
          {isMenuOpen && (
            <>
              <div className="fixed inset-0 z-10" onClick={() => setIsMenuOpen(false)}/>
              <div
                role="menu"
                className="absolute bottom-full left-0 z-20 mb-1 min-w-44 rounded-lg border border-border bg-surface shadow-2xl py-1"
              >
                <button
                  role="menuitem"
                  onClick={() => {
                    setIsMenuOpen(false)
                    setShowEditUserModal(true)
                  }}
                  className="w-full text-left px-4 py-2 text-sm text-text hover:bg-code-bg hover:text-text-h transition-colors duration-150 border-none bg-transparent cursor-pointer"
                >
                  {t('editUser')}
                </button>
                <button
                  role="menuitem"
                  onClick={() => {
                    setIsMenuOpen(false)
                    setShowContactsModal(true)
                  }}
                  className="w-full text-left px-4 py-2 text-sm text-text hover:bg-code-bg hover:text-text-h transition-colors duration-150 border-none bg-transparent cursor-pointer"
                >
                  {t('contacts')}
                </button>
              </div>
            </>
          )}
        </div>
      </div>
      {showEditUserModal && <EditUserModal me={me} onClose={() => setShowEditUserModal(false)}/>}
      {showContactsModal && <ContactsModal onClose={() => setShowContactsModal(false)}/>}
    </aside>
  )
}

export interface Props {
  selectedRoomId?: string;
  setSelectedRoomId: (roomId: string) => void;
  me: User;
}

export default AsideChats;
