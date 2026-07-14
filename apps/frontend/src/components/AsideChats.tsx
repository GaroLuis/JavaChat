import {useState} from "react";
import type {Room} from "../App.tsx";

const AsideChats = ({selectedRoom, setSelectedRoom}: Props) => {
  const [search, setSearch] = useState('')

  const rooms: Room[] = []

  return (
    <aside className="w-80 border-r border-border flex flex-col bg-bg shrink-0">
      <div className="p-5 border-b border-border flex items-center justify-between">
        <h1
          className="m-0 text-2xl font-semibold text-text-h"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          Chats
        </h1>
      </div>
      <div className="px-5 py-3">
        <input
          className="w-full px-3.5 py-2.5 border border-border rounded-lg text-sm bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200"
          type="text"
          placeholder="Search users..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>
      <nav className="flex-1 overflow-y-auto py-1">
        {rooms.map((room) => (
          <button
            key={room.id}
            className={`flex items-center gap-3 w-full px-5 py-3 border-none bg-transparent cursor-pointer text-left text-text hover:bg-code-bg transition-colors duration-150 ${selectedRoom?.id === room.id ? 'bg-accent-bg' : ''}`}
            onClick={() => setSelectedRoom(room)}
          >
            <div
              className="size-11 rounded-full bg-accent text-white flex items-center justify-center font-semibold text-base shrink-0">
              {room.user.username[0]}
            </div>
            <div className="flex-1 min-w-0">
              <div className="text-[15px] font-semibold text-text-h mb-0.5">
                {room.user.username}
              </div>
              <div className="text-[13px] text-text truncate">
                {room.messages[-1].content}
              </div>
            </div>
            <div className="text-xs text-text shrink-0">
              {room.messages[-1].timestamp}
            </div>
          </button>
        ))}
      </nav>
    </aside>
  )
}

interface Props {
  selectedRoom?: Room;
  setSelectedRoom: (room: Room) => void;
}

export default AsideChats;
