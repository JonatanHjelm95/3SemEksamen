import React, { useState } from 'react';
import { FiUser } from 'react-icons/fi';
import './styles.scss';
import facade from '../apiFacade';
import Modal from 'react-bootstrap/Modal';
import { BrowserRouter as Router, NavLink } from 'react-router-dom';



const LoginForm = ({ login, registerNewUser, Register }) => {
  const [user, setUser] = useState({
    username: '',
    password: '',
  });

  const onSubmit = evt => {
    evt.preventDefault();
    login(user.username, user.password);
  };

  const register = evt => {
    evt.preventDefault();
    registerNewUser();

  }

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


      </form>
      <form onSubmit={register} onChange={onChange}>
        <button type="submit" className="login">
          Not a User?
      </button>
      </form>
    </div>
  );
};

const RegisterForm = ({ register }) => {
  const [user, setUser] = useState({
    username: '',
    password: '',
  });

  const onSubmit = evt => {
    evt.preventDefault();
    register(user.username, user.password);
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
        <input placeholder="Confirm Password" id="passwordCheck" />
        <button type="submit" className="login">
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
  const [registerClicked, setRegisterClicked] = useState(false);
  const [registerFormShown, showRegisterForm] = useState(false);


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
    showRegisterForm(false);
    setRegisterClicked(false);

  };

  const registerNewUser = () => {
    showLoginForm(false)
    setRegisterClicked(true);
    showRegisterForm(true)
  }
  const register = async (username, pass) => {
    const { username: resUsername, role: resRole } = await facade.register(
      username,
      pass
    );
    setLoggedIn(true);
    setRole(resRole);
    setUsername(resUsername);
    showLoginForm(false);
    showRegisterForm(false);
    setRegisterClicked(false);
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
      {!registerClicked ? (

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
            <LoginForm login={login} registerNewUser={registerNewUser} />
          </Modal.Body>
        </Modal>
      ) : (
          <Modal
            show={registerFormShown}
            size="md"
            centered
            onHide={() => {
              showRegisterForm(false);
            }}
          >
            <Modal.Header closeButton>
              <Modal.Title id="contained-modal-title-vcenter">
                <FiUser className="modal-icon" />
                Register
      </Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <RegisterForm register={register} />
            </Modal.Body>
          </Modal>)}
    </div>
  );
};

export default Login;
