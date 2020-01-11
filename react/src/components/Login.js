import React, { useState } from 'react';
import { FiUser } from 'react-icons/fi';
import './styles.scss';
import facade from '../apiFacade';
import Modal from 'react-bootstrap/Modal';


const LoginForm = ({ login }) => {
  const [user, setUser] = useState({
    username: '',
    password: '',
  });

  const onSubmit = evt => {
    evt.preventDefault();
    facade.realogin(user.username, user.password);
  };

  const onChange = evt => {
    evt.persist();
    setUser(prevState => ({
      ...prevState,
      [evt.target.id]: evt.target.value,
    }));
  };

  return (
    <div className="LoginForm">
      <form onSubmit={onSubmit} onChange={onChange}>
        <input placeholder="Username" id="username" />
        <input placeholder="Password" id="password" />
        <button type="submit" className="login">
          Login
        </button>
        <button type="submit" className="register">
          Register
        </button>
      </form>
    </div>
  );
};

const UserInfo = ({ username, role, logout }) => {
  const onLogout = evt => {
    evt.preventDefault();
    logout();
  };

  return (
    <div className="UserInfo">
      <p className="header">
        logged in as: {username}, role: {role}
      </p>
      <button onClick={onLogout} className="login">
        Logout
      </button>
    </div>
  );
};

const Login = props => {
  const [username, setUsername] = useState(false);
  const [role, setRole] = useState('');
  const [loggedIn, setLoggedIn] = useState('');
  const [loginFormShown, showLoginForm] = useState(false);

  const Flogout = async () => {
    setLoggedIn(false);
  };

  const login = async (username, pass) => {
    const { username: resUsername, role: resRole } = await facade.login(
      username,
      pass
    );
    setLoggedIn(true);
    setRole(resRole);
    setUsername(resUsername);
    showLoginForm(false);
  };

  return (
    <div className="loginform">
      {loggedIn ? (
        <UserInfo username={username} role={role} logout={Flogout} />
      ) : (
        <button
          className="login"
          onClick={() => {
            showLoginForm(true);
          }}
        >
          <FiUser className="icon" />
          <span>Login</span>
        </button>
      )}

      <Modal
        show={loginFormShown}
        size="md"
        centered
        onHide={() => {
          showLoginForm(false);
        }}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            <FiUser className="modal-icon" />
            Login
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <LoginForm login={login} />
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default Login;
