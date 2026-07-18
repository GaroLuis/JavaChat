import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import {useQueryClient} from "@tanstack/react-query";
import {QUERY_KEYS} from "../api/queryKeys.ts";

const useWsClient = () => {
  const [client, setClient] = useState<Client | null>(null);
  const queryClient = useQueryClient();

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        stompClient.subscribe("/topic/messages", () => {
          queryClient.invalidateQueries({queryKey: [QUERY_KEYS.ROOMS]})
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
  }, [queryClient]);

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
