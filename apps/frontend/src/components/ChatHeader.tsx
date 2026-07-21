import type {User} from "../api/types/User.ts";
import {formatTimestamp} from "../utils/helpers.ts";

const ChatHeader = ({user}: Props) => {

  return (
    <header
      className="flex items-center gap-3 px-6 py-4 border-b border-border bg-bg">
      <div
        className="size-10 rounded-full bg-accent text-white flex items-center justify-center font-semibold shrink-0">
        {user.username[0].toUpperCase()}
      </div>
      <div className="flex-1">
        <div className="text-base font-semibold text-text-h">
          {user.username}
        </div>
        {user.connected && (<div className="text-xs text-green-500">Online</div>)}
        {!user.connected && (
          <div>
            <div className="text-xs text-red-700">Offline</div>
            {user.lastConnection && (<div className="text-xs text-gray-500">{formatTimestamp(user.lastConnection)}</div>)}
          </div>
        )}
      </div>
    </header>
  )
}

interface Props {
  user: User
}

export default ChatHeader;
