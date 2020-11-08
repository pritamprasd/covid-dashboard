import './LoginPage.css';
import React, { useState } from 'react';
import INTERNAL_API from '../_api/covid-internal/internalapi'
import { useHistory } from "react-router-dom";
import { Button, TextField, Grid, Typography, Divider } from '@material-ui/core';
import store from '../storage/store';

store.subscribe((state) => { console.log(state)})

function LoginForm() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [displayLogin, setDisplayLogin] = useState(true);
  const history = useHistory();

  const showSignupPage = (e) => {
    setDisplayLogin(false)
  }
  const showLoginPage = (e) => {
    setDisplayLogin(true)
  }
  const handleSignup = (e) => {
    console.log("Signing you up...")
    const requestBody = {
      "username": username,
      "email": email,
      "password": password
    }
    INTERNAL_API.post(`auth/signup`, requestBody).then(res => {
      if (res.status === 200) {
        alert("Signup Success, Redirecing to Login page")
        setDisplayLogin(true)
      }
    }).catch(
      err => {
        console.error("auth/signup api call failed. " + JSON.stringify(err.response.data).data)
        alert("Signup failed, check username/password are provided.")
        setDisplayLogin(true)
      }
    )
  }

  const handleLogin = (e) => {
    console.log("Signing you in ...")
    const requestBody = {
      "username": username,
      "password": password
    }
    INTERNAL_API.post(`auth/signin`, requestBody).then(res => {
      if (res.status === 200) {
        const jwtString = res.data.jwt;
        console.log("JWT: "+ jwtString)
        store.dispatch({ type: 'jwt', token: jwtString })               
        history.push("/home")
      }
    }).catch(
      err => {
        console.error("signin api call failed. " + err)
        alert("Signin failed: username/password don't match")
      }
    )
  }

  return (
    <div>
      { displayLogin &&
        <div id='loginFormContainer'>
          <form className="root">
            <Grid container spacing={2} direction="column" justify="center" alignItems="center">
              <Grid item >
                <Typography>Login</Typography>
              </Grid>
              <Grid item >
                <TextField id="username" type="text" label="Username" variant="outlined"
                  color="secondary" autoFocus={true} onChange={e => { setUsername(e.target.value) }} />
              </Grid>
              <Grid item >
                <TextField id="pass" type="password" label="Password" variant="outlined"
                  color="secondary" onChange={e => { setPassword(e.target.value) }} />
              </Grid>
              <Grid item>
                <Button onClick={handleLogin} variant="text">Submit</Button>
              </Grid>
              <Grid item>
                <Divider clas="divider" />
              </Grid>
              <Grid item container justify="center" spacing={2}>
                <Grid item>
                  <Typography color="textPrimary" variant="subtitle2">
                    Don't Have an account?
                  </Typography>
                </Grid>
                <Grid item>
                  <Typography onClick={showSignupPage} color="primary" variant="subtitle2">
                    Sign up
                  </Typography>
                </Grid>
              </Grid>
            </Grid>
          </form>
        </div>}
      {
        !displayLogin &&
        <div id='signupFormContainer'>
          <form className="root">
            <Grid container spacing={2} direction="column" justify="center" alignItems="center">
              <Grid item >
                <Typography>Create Account</Typography>
              </Grid>
              <Grid item >
                <TextField id="username" type="text" label="Username" variant="outlined"
                  color="secondary" autoFocus={true} onChange={e => { setUsername(e.target.value) }} />
              </Grid>
              <Grid item >
                <TextField id="email" type="text" label="Email" variant="outlined"
                  color="secondary" onChange={e => { setEmail(e.target.value) }} />
              </Grid>
              <Grid item >
                <TextField id="pass" type="password" label="Password" variant="outlined"
                  color="secondary" onChange={e => { setPassword(e.target.value) }} />
              </Grid>
              <Grid item>
                <Button onClick={handleSignup} variant="text">SIGN UP</Button>
              </Grid>
              <Grid item container justify="center" spacing={2}>
                <Grid item>
                  <Typography color="textPrimary" variant="subtitle2">
                    Already have an account?
                  </Typography>
                </Grid>
                <Grid item>
                  <Typography onClick={showLoginPage} color="primary" variant="subtitle2">
                    Login
                  </Typography>
                </Grid>
              </Grid>
            </Grid>
          </form>
        </div>
      }
    </div >
  );
}

export default LoginForm;
