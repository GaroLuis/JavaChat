import {useForm} from "react-hook-form";
import {useMutation} from "@tanstack/react-query";
import {createUser} from "../../../api/repositories/users.ts";
import {login} from "../../../api/repositories/auth.ts";
import {useTranslation} from "react-i18next";

interface SignUpModalProps {
  onClose: () => void;
}

const SignUpModal = ({onClose}: SignUpModalProps) => {
  const { t } = useTranslation()

  const {
    register,
    handleSubmit,
  } = useForm({
    defaultValues: {username: '', password: ''}
  });

  const signUpMutation = useMutation({
    mutationFn: async (data: {username: string; password: string}) => {
      await createUser(data);
      await login(data);
    },
    onSuccess: async () => {location.reload();}
  })

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
      onClick={onClose}
    >
      <form
        onClick={(e) => e.stopPropagation()}
        onSubmit={handleSubmit((data) => {
          signUpMutation.mutate({username: data.username, password: data.password})
        })}
        className="bg-bg rounded-2xl shadow-xl w-full max-w-sm mx-4 p-6 relative"
        noValidate
      >
        <button
          type="button"
          onClick={onClose}
          aria-label={t('close')}
          className="absolute top-3 right-4 border-none bg-transparent text-text text-2xl leading-none cursor-pointer hover:text-text-h transition-colors duration-200"
        >
          ×
        </button>
        <h2
          className="text-xl font-semibold text-text-h mb-1"
          style={{fontFamily: 'var(--font-heading)'}}
        >
          {t('signUp')}
        </h2>
        <p className="text-sm text-text mb-5">
          {t('signUpDescription')}
        </p>
        <input
          {...register("username")}
          type="text"
          placeholder={t('username')}
          className="w-full px-4 py-2.5 border border-border rounded-lg text-sm bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200 mb-3"
        />
        <input
          {...register("password")}
          type="password"
          placeholder={t('password')}
          className="w-full px-4 py-2.5 border border-border rounded-lg text-sm bg-code-bg text-text-h outline-none focus:border-accent placeholder:text-text transition-colors duration-200 mb-3"
        />
        {signUpMutation.isError && (
          <p role="alert" className="text-sm text-red-500 mb-3">
            {t('signUpError')}
          </p>
        )}
        <button
          type="submit"
          disabled={signUpMutation.isPending}
          className="w-full py-2.5 border-none rounded-lg bg-accent text-white text-sm font-semibold cursor-pointer hover:opacity-90 disabled:opacity-60 transition-opacity duration-200"
        >
          {t('signUp')}
        </button>
      </form>
    </div>
  )
}

export default SignUpModal
