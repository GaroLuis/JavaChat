export function formatTimestamp(isoString: string): string {

  return new Date(isoString).toLocaleDateString('en-US',
    new Date(isoString).getFullYear() === new Date().getFullYear()
      ? { month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric' }
      : { month: 'short', day: 'numeric', year: 'numeric', hour: 'numeric', minute: 'numeric' }
  );
}
