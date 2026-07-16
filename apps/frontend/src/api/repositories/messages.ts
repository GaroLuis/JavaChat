import connector from "../connector.ts";

export function sendMessages() {
  return connector.post('/messages')
}
