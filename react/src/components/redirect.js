import React from 'react'
import { Redirect } from 'react-router-dom'
class redirect extends React.Component {
  state = {
    redirect: true
  }
  renderRedirect = () => {
    if (this.state.redirect) {
      return <Redirect to='CA3/documentation/' />
    }
  }
  render () {
    return (
       <div>
        <a href="documentation/"> Documentation </a>
       </div>
    )
  }
}

export default redirect