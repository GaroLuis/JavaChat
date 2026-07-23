import {useForm} from "react-hook-form";
import {useContext} from "react";
import {WsClientContext} from "../contexts/WsClientProvider.tsx";
import * as yup from "yup";
import {yupResolver} from "@hookform/resolvers/yup";

const SCHEMA = yup.object({
  input: yup.string().required(),
})

const ChatInput = ({roomId}: ChatInputProps) => {
  const {sendMessage} = useContext(WsClientContext)

  const {
    register,
    setValue,
    handleSubmit,
    formState: { isValid },
  } = useForm({
    defaultValues: {input: ''},
    resolver: yupResolver(SCHEMA)
  });

  return (
    <form
      onSubmit={handleSubmit((data) => {
        sendMessage({content: data.input, roomId: roomId})
        setValue('input', '')
      })}
      noValidate
    >
      <div className="flex gap-2 px-6 py-4 border-t border-white bg-white">
        <input
          {...register("input")}
          className="flex-1 px-4 py-2.5 border border-border rounded-full text-[15px] bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200"
          type="text"
          placeholder="Type a message..."
        />
        <button
          className="px-5 py-2.5 border-none rounded-full bg-accent text-white text-sm font-semibold cursor-pointer hover:opacity-90 disabled:opacity-50 disabled:cursor-not-allowed shrink-0 transition-opacity duration-200"
          type={'submit'}
          disabled={!isValid}
        >
          Send
        </button>
      </div>
    </form>
  )
}

export default ChatInput;

interface ChatInputProps {
  roomId: string
}
