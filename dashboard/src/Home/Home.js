import './Home.css';
import React, { useState } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import DashboardContainer from './Dashboard/DashboardContainer';
import store from "../storage/store"
import { useHistory } from "react-router-dom";
import ProfileDataContainer from './profile/ProfileDataContainer';

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

    return (
        <div>
            <AppBar position="static" >
                <Toolbar className="appbar">
                    <Typography variant="h6" className="title" onClick={showDashBoard}> Covid Dashboard </Typography>
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
                <ProfileDataContainer />
            }
        </div>
    )
}

export default Home