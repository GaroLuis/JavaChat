import {useQuery} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {getContacts} from "../api/repositories/users.ts";

export const useGetContacts = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.CONTACTS],
    queryFn: getContacts,
  })
}
