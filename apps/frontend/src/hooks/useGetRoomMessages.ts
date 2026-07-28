import {useInfiniteQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getRoomMessages} from "../api/repositories/messages.ts";
import type {Room} from "../api/types/Room.ts";
import type {Message} from "../api/types/Message.ts";

const pages = 20;

export const useGetRoomMessages = (room: Room) => {
  return useInfiniteQuery<Message[]>({
    queryKey: [QUERY_KEYS.ROOMS, {id: room.id}],
    queryFn: async ({pageParam}) => {
      const {data} = await getRoomMessages(room.id, pages, pageParam as string | undefined);
      return data;
    },
    initialPageParam: undefined as string | undefined,
    getNextPageParam: (lastPage ) => {
      if (lastPage.length < pages) {
        return undefined;
      }

      return lastPage[lastPage.length - 1].timestamp;
    },
  })
}