import './Signup.css';
import React, { useState } from 'react';
import INTERNAL_API from '../_api/covid-internal/internalapi'


function SignUpForm(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repassword, setRepassword] = useState("");
  const [acceptedTerms, setAcceptedTerms] = useState(false);
  return (
    <div id='signUpFormContainer'>
      <form id="signUpForm">
        <input className='signUpRow' type="email" placeholder="email" onChange={e => { setEmail(e.target.value) }} />
        <input className='signUpRow' type="password" placeholder="password" onChange={e => { setPassword(e.target.value) }} />
        <input className='signUpRow' type="repassword" placeholder="repassword" onChange={e => { setRepassword(e.target.value) }} />
        <input className='signUpRow' type='checkbox' placeholder="checkbox" onChange={e => { setAcceptedTerms(e.target.value) }} />
        <button type='button' onClick={e => handleSignup(email, password, repassword, acceptedTerms)}>SignUp</button>
      </form>
    </div>
  );
}

function handleSignup(email, password, repassword, acceptedTerms) {
  console.log("Signing you up...")
  const requestBody = {
    "username": email,
    "email": email,
    "password": password
  }
  INTERNAL_API.post(`auth/signup`, requestBody).then(res => {
    if (res.status === 200) {
      alert("Signup success")
    }
  }).catch(
    err => {      
      console.error("auth/signup api call failed. " + JSON.stringify(err.response.data).data)
      alert("Signup failed: "+ err.response.data)
    }
  )
}

export default SignUpForm;
