import './Profile.css';
import INTERNAL_API from '../../_api/covid-internal/internalapi'
import React, { useEffect, useState } from 'react';
import store from "../../storage/store"
import { Button, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from '@material-ui/core';
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import { useHistory } from 'react-router';

store.subscribe(() => { })

function Profile({ data }) {
  // const history = useHistory();
  const [editEnabled, setEditEnabled] = useState(false)
  // const [email, setEmail] = useState(false)

  // const updateEmail = (e) => {
  //   setEmail(e.target.value)
  // }

  const getValueField = (k) => {
    if (typeof data[k] === "boolean") {
      return (data[k] === true ? <CheckIcon /> : <CloseIcon />)
    } else {
      switch (k) {
        // email edit allowed
        case "email":
          return editEnabled ? (<TextField key={"editable-" + k} defaultValue={data[k]} ></TextField>) : (<Typography key={"readonly-" + k}>{data[k]}</Typography>)
        default:
          return (<Typography key={"readonly-" + k}>{data[k]}</Typography>)
      }
    }
  }

  const onEditEnableButtonClick = (e) => {
    setEditEnabled(true)
  }

  const updateUserInfo = (e) => {
    setEditEnabled(false)
    // let token = store.getState().auth.token;
    // if (token === '') {
    //   console.log("No token found")
    //   history.push("/login")
    // }
    // const requestBody = {
    //   "userName": "",
    //   "email": email
    // }
    // INTERNAL_API.put('/userinfo', requestBody).then(res => {
    //   if (res.status === 200) {
    //     //console.log("GET profile response: " + JSON.stringify(res.data) || 'N/A')
    //     // setProfileData(res.data)
    //   }
    // }).catch(
    //   err => {
    //     console.error("GET profile info call failed, retry again" + err)
    //     history.push("/login")
    //   }
    // )
  }

  const getEditButtons = () => {
    if (editEnabled) {
      return (<Button variant="outlined" onClick={updateUserInfo}>Update Information</Button>)
    } else {
      return (<Button variant="outlined" onClick={onEditEnableButtonClick}>Enable Edit</Button>)
    }
  }

  const createRows = () => {
    console.log("rendering...")
    var profileDataMap = []
    Object.keys(data).forEach(key => {
      console.log("Key found : " + key)
      profileDataMap.push(key)
    })
    return (
      <Grid container direction="column" spacing={4} >
        {
          profileDataMap.map((k, i) => {
            return (
              <Grid container item key="{k}">
                <Grid item xs={6} >
                  <Typography>{k}</Typography>
                </Grid>
                <Grid item xs={6}>{getValueField(k)}</Grid>
              </Grid>
            )
          })
        }
        <Grid container item justify="center">
          {getEditButtons()}
        </Grid>
      </Grid>
    )
  }
  return (
    <div>
      {
        createRows()
      }
    </div>
  );
}

export default Profile;