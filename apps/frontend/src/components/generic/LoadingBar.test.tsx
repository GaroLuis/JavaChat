import { render } from '@testing-library/react'
import { describe, it, expect } from 'vitest'
import LoadingBar from './LoadingBar'

describe('LoadingBar', () => {
  it('renders the loading bar', () => {
    const { container } = render(<LoadingBar />)
    expect(container.firstChild).toBeTruthy()
  })

  it('renders with custom className', () => {
    const { container } = render(<LoadingBar className="my-custom-class" />)
    const wrapper = container.firstChild as HTMLElement
    expect(wrapper.className).toContain('my-custom-class')
  })

  it('renders without className', () => {
    const { container } = render(<LoadingBar />)
    const wrapper = container.firstChild as HTMLElement
    expect(wrapper).toBeTruthy()
    expect(wrapper.children.length).toBe(1)
  })
})
