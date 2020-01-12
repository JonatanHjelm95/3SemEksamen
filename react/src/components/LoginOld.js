import React, { useState } from 'react';
import { FiUser } from 'react-icons/fi';
import { NavLink } from 'react-router-dom';
import Loader from './Loader';
import './styles.scss';
import facade from '../apiFacade';
import Modal from 'react-bootstrap/Modal';

const LoginForm = ({ login }) => {
  const [user, setUser] = useState({
    username: '',
    password: '',
  });

  const [form, setform] = useState({
    formtype: "Login"
  });

  const onSubmit = evt => {
    evt.preventDefault();
    login(user.username, user.password);
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
        <input placeholder="username" id="username" />
        <input placeholder="password" type="password" id="password" />
        <button type="submit" className="login">
          Login
        </button>
      </form>
    </div>
  );
};

const RegisterForm = ({ register }) => {
  const [user, setUser] = useState({
    username: '',
    password: '',
    passwordConfirm: ''
  });

  const onSubmit = evt => {
    evt.preventDefault();
    if (user.password == user.passwordConfirm) {
      register(user.username, user.password);
    } else {
      alert("Passwords didn't match");
    }
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
        <input placeholder="username" id="username" />
        <input placeholder="password" type="password" id="password" />
        <input placeholder="confirm password" type="password" id="passwordConfirm" />
        <button type="submit" className="login">
          Create User
        </button>
      </form>
    </div>
  );
};

const UserInfo = ({ username, role, Flogout }) => {
  const onLogout = evt => {
    evt.preventDefault();
    Flogout();
  };

  return (
    <div className="UserInfo">
      <ul className="UserInfoList">
        <li>
          <NavLink className="link" to="/user/123">
            <FiUser className="modal-icon-white" /><span className="profileLabel">{username}</span>
          </NavLink>
        </li>
        <li>
          <button onClick={onLogout} className="login">
            <span>Logout</span>
          </button>
        </li>
      </ul>
    </div>
  );
};

const LoginOld = props => {
  const [username, setUsername] = useState(false);
  const [role, setRole] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [loginFormShown, showLoginForm] = useState(false);
  const [formtype, formtypechange] = useState('Login');
  const [loading, setLoading] = useState(false);

  const Flogout = () => {
    setLoggedIn(false);
    console.log(loggedIn);
    facade.logout();
  };

  const logout = async () => {
    await facade.logout();
    setLoggedIn(false)
    console.log(loggedIn)
  }

  const login = async (username, pass) => {
    try {
      setLoading(true);
      let user = await facade.login(username, pass)
      //setRole(user.role);
      setLoggedIn(true);
      setRole(user.role);
      setUsername(user.username);
      showLoginForm(false);
    }
    catch (error) {
      alert("username or password wrong");
      setLoading(false);
    }
    
  };



  const register = async (username, pass) => {
    try {
      setLoading(true);
      let user = await facade.register(username, pass)
      setLoading(false);
      setLoggedIn(true);
      setRole(user.role);
      setUsername(user.username);
      showLoginForm(false);
    }
    catch (error) {
      alert("username already exists in database");
      setLoading(false);
    }
  }


  return (
    <div className="loginform">
      {loggedIn ? (
        <UserInfo username={username} role={role} Flogout={Flogout} />
      ) : (
          <button
            className="login"
            onClick={() => {
              showLoginForm(true);
              setLoggedIn(false)
              console.log(loggedIn)
              formtypechange('Login')
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
            {formtype}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {loading ? (<div className="LoginForm"><Loader /></div>) : (formtype == 'Login') ?
            (<div><LoginForm login={login} />
              <button
                className="register"
                onClick={() => formtypechange('Opret Ny Bruger')}
              >
                <span>Ny Bruger</span>
              </button>
            </div>
            ) : (
              <RegisterForm register={register} />
            )}
        </Modal.Body>
      </Modal>

    </div>
  );
};

export default LoginOld;
