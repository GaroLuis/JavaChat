import connector from "../connector.ts";
import type {Message} from "../types/Message.ts";

export function sendMessages() {
  return connector.post('/messages')
}

export function getRoomMessages(roomId: string) {
  return connector.get<Message[]>(`/rooms/${roomId}/messages`)
}
