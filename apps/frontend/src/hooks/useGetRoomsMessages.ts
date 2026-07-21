import {useQueries} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import type {Room} from "../api/types/Room.ts";
import {getRoomMessages} from "../api/repositories/messages.ts";

export const useGetRoomsMessages = (rooms: Room[]) => {
  return useQueries({
    queries: rooms.map((room) => {
      return {
        queryKey: [QUERY_KEYS.ROOMS, { id: room.id }],
        queryFn: () => getRoomMessages(room.id),
      }
    })
  })
}