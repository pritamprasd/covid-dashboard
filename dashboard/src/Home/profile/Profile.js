import './Profile.css';
import React from 'react';
import store from "../../storage/store"

store.subscribe(() => console.log(store.getState().auth.token))

function Profile() {
  return (
    <React.Fragment id='profileContainer'>
      <h1>Profile Page</h1>
    </React.Fragment>
  );
}

export default Profile;