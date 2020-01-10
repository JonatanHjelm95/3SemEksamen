import React, { Component } from 'react';
import facade from './apiFacade';
import Navigation from './components/Navigation';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { loggedIn: false, username: '', role: '' };
  }
  logout = () => {
    facade.logout();
    this.setState({ loggedIn: false });
  };

  login = async (username, pass) => {
    const res = await facade.login(username, pass);
    this.setState({ loggedIn: true, username: username, role: res.role });
  };

  render() {
    return (
      <div>
        <Navigation />
      </div>
    );
  }
}
export default App;
