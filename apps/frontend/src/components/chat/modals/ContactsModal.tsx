import AsyncSelect from "react-select/async";
import {useEffect, useMemo} from "react";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {addContact, getContacts, getUsers, removeContact} from "../../../api/repositories/users.ts";
import {QUERY_KEYS} from "../../../api/queryKeys.ts";
import {useGetContacts} from "../../../hooks/useGetContacts.ts";
import {useTranslation} from "react-i18next";
import debounce from "lodash/debounce";
import type {User} from "../../../api/types/User.ts";

interface UserOption {
  value: string;
  label: string;
}

interface ContactsModalProps {
  onClose: () => void;
}

const ContactsModal = ({onClose}: ContactsModalProps) => {
  const { t } = useTranslation()
  const queryClient = useQueryClient()
  const contactsQuery = useGetContacts()
  const contacts = contactsQuery.data?.data ?? []

  const invalidateContacts = () => queryClient.invalidateQueries({queryKey: [QUERY_KEYS.CONTACTS]})

  const addContactMutation = useMutation({
    mutationFn: (contactId: string) => addContact(contactId),
    onSuccess: invalidateContacts,
  })

  const removeContactMutation = useMutation({
    mutationFn: (contactId: string) => removeContact(contactId),
    onSuccess: invalidateContacts,
  })

  const loadUsers = useMemo(() => {
    let latestRequest = 0

    return debounce((inputValue: string, callback: (options: UserOption[]) => void) => {
      if (inputValue.length < 1) {
        callback([])
        return
      }

      const requestId = ++latestRequest

      Promise.all([getUsers(inputValue), getContacts()])
        .then(([{data: users}, {data: currentContacts}]) => {
          if (requestId !== latestRequest) {
            return
          }

          const contactIds = new Set(currentContacts.map((c: User) => c.id))
          callback(
            users
              .filter((u: User) => !contactIds.has(u.id))
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

  useEffect(() => () => loadUsers.cancel(), [loadUsers])

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
      onClick={onClose}
    >
      <div
        onClick={(e) => e.stopPropagation()}
        className="bg-bg rounded-2xl shadow-xl w-full max-w-sm mx-4 p-6 relative"
      >
        <button
          type="button"
          onClick={onClose}
          aria-label={t('close')}
          className="absolute top-3 right-4 border-none bg-transparent text-text text-2xl leading-none cursor-pointer hover:text-text-h transition-colors duration-200"
        >
          ×
        </button>
        <h2
          className="text-xl font-semibold text-text-h mb-4"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          {t('contacts')}
        </h2>
        <AsyncSelect
          cacheOptions
          defaultOptions={false}
          loadOptions={loadUsers}
          onChange={async (option: UserOption | null) => {
            if (!option) {
              return
            }

            await addContactMutation.mutateAsync(option.value)
          }}
          placeholder={t('searchUsers')}
          noOptionsMessage={() => null}
          loadingMessage={() => t('searching')}
          value={null}
        />
        <div className="mt-4 max-h-64 overflow-y-auto">
          {contacts.map((contact: User) => (
            <div key={contact.id} className="flex items-center gap-3 px-2 py-2 rounded-lg hover:bg-code-bg transition-colors duration-150">
              <div
                className="size-9 rounded-full bg-accent text-white flex items-center justify-center font-semibold text-sm shrink-0">
                {contact.username[0].toUpperCase()}
              </div>
              <div className="flex-1 min-w-0 text-[15px] text-text-h truncate">
                {contact.username}
              </div>
              <button
                type="button"
                onClick={() => removeContactMutation.mutate(contact.id)}
                aria-label={t('removeContact')}
                title={t('removeContact')}
                className="size-6 flex items-center justify-center rounded hover:bg-red-500/20 text-text hover:text-red-500 transition-colors duration-150 cursor-pointer border-none bg-transparent shrink-0"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <line x1="18" y1="6" x2="6" y2="18"/>
                  <line x1="6" y1="6" x2="18" y2="18"/>
                </svg>
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}

export default ContactsModal
