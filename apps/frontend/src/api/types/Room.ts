import type {User} from "./User.ts";
import type {Message} from "./Message.ts";

export interface Room {
  id: string
  users: User[]
  messages: Message[]
}
