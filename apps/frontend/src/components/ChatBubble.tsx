import type {Message, User} from "../App.tsx";

const ChatBubble = ({message, user}: Props) => {

  return (
    <div
      className={`max-w-[70%] px-3.5 py-2.5 rounded-2xl text-[15px] leading-snug ${message.sender.id === user.id ? 'bg-accent text-white rounded-br' : 'bg-[var(--color-code-bg)] text-[var(--color-text-h)] rounded-bl'}`}
    >
      <div className="wrap-break-word">{message.content}</div>
      <div className="text-[11px] opacity-70 mt-1 text-right">
        {message.timestamp}
      </div>
    </div>
  )
}

interface Props {
  message: Message;
  user: User;
}

export default ChatBubble;
