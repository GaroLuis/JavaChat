import {createContext, useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import {QUERY_KEYS} from "../api/queryKeys.ts";
import {useQueryClient} from "@tanstack/react-query";
import {useGetMe} from "../hooks/useGetMe.ts";

// eslint-disable-next-line react-refresh/only-export-components
export const WsClientContext = createContext<WsClientContextType>({
  sendMessage: () => null,
  client: null,
});

const WsClientProvider = ({children}: WsClientProviderProps) => {
  const [client, setClient] = useState<Client | null>(null);
  const queryClient = useQueryClient();
  const meQuery = useGetMe();
  const meId = meQuery.data?.data?.id ?? null;

  useEffect(() => {
    if (null === meId) {
      return;
    }

    const stompClient = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        stompClient.subscribe(`/user/queue/messages`, (msg) => {
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
  }, [meId, queryClient]);

  const sendMessage = (chatMessage: SendMessage) => {
    if (client) {
      client.publish({ destination: "/app/send-message", body: JSON.stringify(chatMessage) });
    }
  };

  return (
    <WsClientContext.Provider value={{sendMessage, client}}>
      {children}
    </WsClientContext.Provider>
  )
}

export default WsClientProvider;

interface WsClientProviderProps {
  children: React.ReactNode
}

export interface SendMessage {
  roomId: string;
  content: string;
}

interface WsClientContextType {
  sendMessage: (message: SendMessage) => void;
  client: Client | null;
}
