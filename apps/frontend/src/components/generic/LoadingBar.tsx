const LoadingBar = ({ className }: LoadingBarProps) => {
  return (
    <div className={`h-1 w-full overflow-hidden bg-accent-bg ${className ?? ''}`}>
      <div className="size-full animate-[loading-bar_1.5s_ease-in-out_infinite] bg-accent" />
    </div>
  )
}

interface LoadingBarProps {
  className?: string
}

export default LoadingBar
