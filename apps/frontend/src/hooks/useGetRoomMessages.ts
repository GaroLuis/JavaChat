import {useQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getRoomMessages} from "../api/repositories/messages.ts";
import type {Room} from "../api/types/Room.ts";

export const useGetRoomMessages = (room: Room) => {
  return useQuery({
    queryKey: [QUERY_KEYS.ROOMS, {id: room.id}],
    queryFn: () => getRoomMessages(room.id)
  })
}