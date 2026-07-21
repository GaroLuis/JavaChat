import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import {useQueryClient} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {useGetMe} from "./useGetMe.ts";

const useWsClient = () => {
  const [client, setClient] = useState<Client | null>(null);
  const queryClient = useQueryClient();
  const meQuery = useGetMe();

  const me = meQuery.data!.data;

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        stompClient.subscribe(`/user/${me.id}/queue/messages`, (msg) => {
          console.log(JSON.parse(msg.body).room.id);

          queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS, {id: JSON.parse(msg.body).room.id}]})
        });
      },
    });

    stompClient.activate();
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setClient(stompClient);

    return () => {
      stompClient.deactivate();
      setClient(null);
    };
  }, [me.id, queryClient]);

  const sendMessage = (chatMessage: SendMessage) => {
    if (client) {
      client.publish({ destination: "/app/send-message", body: JSON.stringify(chatMessage) });
    }
  };

  return { sendMessage };
}

export default useWsClient;

export interface SendMessage {
  roomId: string;
  content: string;
}
