import {useQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getMe} from "../api/repositories/users.ts";

export const useGetMe = () => {
  return  useQuery({
    queryKey: [QUERY_KEYS.ME],
    queryFn: getMe,
  })
}