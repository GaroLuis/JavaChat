import type {User} from "./User.ts";
import type {Room} from "./Room.ts";

export interface Message {
  id: string;
  content: string;
  timestamp: string
  sender: User
  room: Room
}
