import connector from "../connector.ts";
import type {Room} from "../types/Room.ts";

export async function getRooms() {
  return await connector.get<Room[]>('/rooms')
}

export function deleteRoom(roomId: string) {
  return connector.delete(`/rooms/${roomId}`)
}

export function createRoom(userIds: string[]) {
  return connector.post('/rooms', {userIds})
}
