import './DashboardContainer.css';
import React, { useState, useEffect } from 'react';
import INTERNAL_API from '../../_api/covid-internal/internalapi'
import { useHistory } from "react-router-dom";
import store from "../../storage/store"

function DashboardContainer() {
  return (
    <div id='dashboardContainer'>
      <h1>Hello</h1>
      ...      
      <h1>Bye</h1>
    </div>
  );
}

export default DashboardContainer;