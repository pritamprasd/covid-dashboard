import React, { useEffect, useState } from 'react'
import store from "../../storage/store"
import Profile from './Profile'
import INTERNAL_API from '../../_api/covid-internal/internalapi'
import { useHistory } from 'react-router'


store.subscribe(() => { })

function ProfileDataContainer() { 
    const history = useHistory();

    const[profileData , setProfileData] = useState({})

    useEffect(() => {
        console.log("ProfileContainer, useEffect called..")    
        let token = store.getState().auth.token;
        if (token === '') {
          console.log("No token found")
          history.push("/login")
        }        
        let data = {}
        const config = {
            headers: {
                "Authorization": "Bearer " + token
            }
        }
        INTERNAL_API.get('/userinfo', config).then(res => {
            if (res.status === 200) {
                //console.log("GET profile response: " + JSON.stringify(res.data) || 'N/A')
                setProfileData(res.data)
            }
        }).catch(
            err => {
                console.error("GET profile info call failed, retry again" + err)
                history.push("/login")
            }
        )
    }, [])

    return (
        <Profile data={profileData} />
    )
}

export default ProfileDataContainer;