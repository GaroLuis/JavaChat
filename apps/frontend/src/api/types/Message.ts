import type {User} from "./User.ts";

export interface Message {
  id: string;
  content: string;
  timestamp: string
  sender: User
}
