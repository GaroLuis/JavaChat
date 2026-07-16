const ChatHeader = ({name}: Props) => {

  return (
    <header
      className="flex items-center gap-3 px-6 py-4 border-b border-border bg-bg">
      <div
        className="size-10 rounded-full bg-accent text-white flex items-center justify-center font-semibold shrink-0">
        {name[0].toUpperCase()}
      </div>
      <div className="flex-1">
        <div className="text-base font-semibold text-text-h">
          {name}
        </div>
        <div className="text-xs text-green-500">Online</div>
      </div>
    </header>
  )
}

interface Props {
  name: string
}

export default ChatHeader;
