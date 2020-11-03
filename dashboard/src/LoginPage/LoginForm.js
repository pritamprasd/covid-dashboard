import './LoginPage.css';
import React, { useState } from 'react';
import INTERNAL_API from '../_api/covid-internal/internalapi'
import ls from 'local-storage'
import { useHistory } from "react-router-dom";


function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();
  return (
    <div id='loginFormContainer'>
      <form id="loginForm">
        <input className='loginRow' type="email" placeholder="email" onChange={e => { setEmail(e.target.value) }} />
        <input className='loginRow' type="password" placeholder="password" onChange={e => { setPassword(e.target.value) }} />
        <button type='button' onClick={e => handleSignup(email, password,history)}>SignIn</button>
      </form>
    </div>
  );
}

function handleSignup(email, password,history) {
  console.log("Signing you in ...")
  const requestBody = {
    "username": email,
    "password": password
  }
  INTERNAL_API.post(`auth/signin`, requestBody).then(res => {
    if (res.status === 200) {
      const jwtString = res.data.jwt;
      ls.set('token', jwtString)
      history.push({
        pathname: '/home',
        state: { 
          timestamp: 0, 
          text : "Login Successful "+ res.data.username
        }
      })
    }
  }).catch(
    err => {      
      console.error("signin api call failed. " + JSON.stringify(err.response.data).data)
      history.push({
        pathname: '/home',
        state: { 
          timestamp: 0, 
          text : "Login Failed "+ JSON.stringify(err.response.data.error)
        }
      })
    }
  )
}

export default LoginForm;
