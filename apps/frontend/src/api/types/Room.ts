import type {User} from "./User.ts";

export interface Room {
  id: string
  users: User[]
}
