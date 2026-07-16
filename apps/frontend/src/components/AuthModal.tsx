import {useForm} from "react-hook-form";
import {useMutation} from "@tanstack/react-query";
import {login} from "../api/repositories/auth.ts";

const AuthModal = () => {

  const {
    register,
    handleSubmit,
  } = useForm({
    defaultValues: {username: '', password: ''}
  });

  const loginMutation = useMutation({
    mutationFn: login,
    onSuccess: async () => {location.reload();}
  })

  return (
    <div className="flex-1 flex items-center justify-center bg-bg">
      <form
        onSubmit={handleSubmit((data) => {
          loginMutation.mutate({username: data.username, password: data.password})
        })}
        className="bg-bg rounded-2xl shadow-xl w-full max-w-sm mx-4 p-6"
        noValidate
      >
        <h2
          className="text-xl font-semibold text-text-h mb-1"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          Sign in
        </h2>
        <p className="text-sm text-text mb-5">
          This chat is locked. Enter your credentials to continue.
        </p>
        <input
          {...register("username")}
          type="text"
          placeholder="Username"
          className="w-full px-4 py-2.5 border border-border rounded-lg text-sm bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200 mb-3"
        />
        <input
          {...register("password")}
          type="password"
          placeholder="Password"
          className="w-full px-4 py-2.5 border border-border rounded-lg text-sm bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200 mb-3"
        />
        <button
          type="submit"
          className="w-full py-2.5 border-none rounded-lg bg-accent text-white text-sm font-semibold cursor-pointer hover:opacity-90 transition-opacity duration-200"
        >
          Sign in
        </button>
      </form>
    </div>
  )
}

export default AuthModal
