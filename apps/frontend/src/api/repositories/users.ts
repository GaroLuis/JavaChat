import connector from "../connector.ts";
import type {User} from "../types/User.ts";

export function getUsers(search: string) {
  return connector.get<User[]>('/users', {params: {s: search}})
}

export function getMe() {
  return connector.get<User>('/users/me')
}
