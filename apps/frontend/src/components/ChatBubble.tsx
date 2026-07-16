
import type {User} from "../api/types/User.ts";
import type {Message} from "../api/types/Message.ts";
import {formatTimestamp} from "../utils/helpers.ts";


const ChatBubble = ({message, user}: Props) => {

  return (
    <div
      className={`max-w-[70%] px-3.5 py-2.5 rounded-2xl text-[15px] leading-snug ${message.sender.id === user.id ? 'bg-accent text-white rounded-br' : 'bg-[var(--color-code-bg)] text-[var(--color-text-h)] rounded-bl'}`}
    >
      <div className="wrap-break-word">{message.content}</div>
      <div className="text-[11px] opacity-70 mt-1 text-right">
        {formatTimestamp(message.timestamp)}
      </div>
    </div>
  )
}

interface Props {
  message: Message;
  user: User;
}

export default ChatBubble;
