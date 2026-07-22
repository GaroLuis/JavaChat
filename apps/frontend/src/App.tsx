import {useContext} from 'react'
import AuthModal from './components/AuthModal.tsx'
import {useGetMe} from "./hooks/useGetMe.ts";
import LoadingBar from "./components/LoadingBar.tsx";
import {WsClientContext} from "./contexts/WsClientProvider.tsx";
import ChatApp from "./components/ChatApp.tsx";

function App() {
  return (
    <div className="flex h-svh max-h-svh overflow-hidden">
      <Content/>
    </div>
  )
}

const Content = () => {
  const {client} = useContext(WsClientContext)

  const meQuery = useGetMe()
  const me = meQuery.data?.data

  if (meQuery.isLoading) {
    return (
      <LoadingBar/>
    )
  }

  if (null == me || !client?.active) {
    return (
      <AuthModal/>
    )
  }

  return (<ChatApp me={me}/>)
}

export default App
