import connector from "../connector.ts";
import type {User} from "../types/User.ts";

export function getUsers(search: string) {
  return connector.get<User[]>('/users', {params: {s: search}})
}

export function getMe() {
  return connector.get<User>('/users/me')
}

export function createUser(data: { username: string; password: string }) {
  return connector.post<User>('/users', data)
}

export function updateMe(data: { username?: string; password?: string }) {
  return connector.put<User>('/users/me', data)
}

export function getContacts() {
  return connector.get<User[]>('/users/contacts')
}

export function addContact(contactId: string) {
  return connector.post('/users/contacts', {contactId})
}

export function removeContact(contactId: string) {
  return connector.delete(`/users/contacts/${contactId}`)
}
