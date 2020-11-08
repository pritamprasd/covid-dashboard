import './Home.css';
import React, { useState, useEffect } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import DashboardContainer from './Dashboard/DashboardContainer';
import store from "../storage/store"
import INTERNAL_API from '../_api/covid-internal/internalapi'
import { useHistory } from "react-router-dom";
import Profile from './profile/Profile';

store.subscribe(() => { })

function Home(props) {
    const history = useHistory();

    const [anchorEl, setAnchorEl] = useState(null);
    const [showProfileState, setShowProfileState] = React.useState(false);
    const [showDashboardState, setShowDashboardState] = React.useState(true);

    const open = Boolean(anchorEl);
    const handleMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const showDashBoard = () => {
        setAnchorEl(null);
        setShowDashboardState(true)
        setShowProfileState(false)
    };
    const showProfile = () => {
        setAnchorEl(null);
        setShowProfileState(true)
        setShowDashboardState(false)
    };
    const gotoLoginPage = () => {
        setAnchorEl(null);
        store.dispatch({ type: 'jwt', token: '' })
        history.push("/login")
    }

    useEffect(() => {
        let token = store.getState().auth.token;
        //   console.log("Home token: "+ token)
        if (token === '') {
            history.push("/login")
        }
        INTERNAL_API.post(`auth/validate`, token).then(res => {
            if (res.status === 200) {
                console.log("Cached token not expired, using old token")
            }
        }).catch(
            err => {
                console.error("token from storage not valid, login again " + err)
                history.push("/login")
            }
        )
    })
    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" className="title" onClick={showDashBoard}> Dashboard </Typography>
                    <div>
                        <IconButton aria-label="account of current user" aria-controls="menu-appbar"
                            aria-haspopup="true" onClick={handleMenu} color="inherit" >
                            <AccountCircle />
                        </IconButton>
                        <Menu id="menu-appbar" anchorEl={anchorEl}
                            keepMounted open={open} onClose={handleClose}>
                            <MenuItem onClick={showProfile}>Profile</MenuItem>
                            <MenuItem onClick={showDashBoard}>Dashboard</MenuItem>
                            <MenuItem onClick={gotoLoginPage}>Logout</MenuItem>
                        </Menu>
                    </div>
                </Toolbar>
            </AppBar>
            { showDashboardState &&
                <DashboardContainer />
            }
            { showProfileState &&
                <Profile />
            }
        </div>
    )
}

export default Home