import React from 'react';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';
import About from './About/About';
import Home from './Home/Home';
import LoginPage from './LoginPage/LoginPage';
import Signup from './Signup/Signup';


export const Routes = () => {
  return (
    <div className="Routes">
      <BrowserRouter>
        <Switch>
          <Route exact path="/home" component={Home} />
          <Route exact path="/">
            <Redirect to="/home" />
          </Route>
          <Route exact path="/about" component={About} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/login" component={LoginPage} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default Routes;
