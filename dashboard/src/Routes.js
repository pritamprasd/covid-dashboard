import React from 'react';
import { BrowserRouter, Route, Redirect } from 'react-router-dom';
import Home from './Home/Home';
import LoginPage from './LoginPage/LoginPage';



export const Routes = () => {
  return (
    <BrowserRouter>
      <div className="Routes">
          <Route exact path="/home" component={Home} />
          <Route exact path="/">
            <Redirect to="/home" />
          </Route>
          <Route exact path="/login" component={LoginPage} />
      </div>
    </BrowserRouter>
  );
}

export default Routes;
