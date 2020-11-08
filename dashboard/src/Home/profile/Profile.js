import './Profile.css';
import React, { useState, useEffect } from 'react';
import INTERNAL_API from '../../_api/covid-internal/internalapi'
import { useHistory } from "react-router-dom";
import store from "../../storage/store"

store.subscribe(() => console.log(store.getState().auth.token))

function Profile() {
  return (
    <div id='profileContainer'>
      <h1>Profile Page</h1>
    </div>
  );
}

export default Profile;