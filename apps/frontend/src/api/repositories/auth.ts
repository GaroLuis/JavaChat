import connector from "../connector.ts";

export function login(data: { username: string; password: string }) {
  return connector.post('/auth/login', data)
}
