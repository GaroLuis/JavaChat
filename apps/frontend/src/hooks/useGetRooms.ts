import {useQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getRooms} from "../api/repositories/rooms.ts";

export const useGetRooms = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.ROOMS],
    queryFn: getRooms
  })
}