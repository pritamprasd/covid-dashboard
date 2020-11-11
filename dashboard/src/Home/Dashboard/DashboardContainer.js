import './DashboardContainer.css';
import React, { useState, useEffect } from 'react';
import INTERNAL_API from '../../_api/covid-internal/internalapi'
import { useHistory } from "react-router-dom";
import store from "../../storage/store"
import { FormControl, Grid, InputLabel, MenuItem, Select } from '@material-ui/core';
import InfoTile from './InfoTile/InfoTile';
import DatewiseGraph from './graphs/DatewiseGraph';
import { MuiPickersUtilsProvider, KeyboardDatePicker } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';

store.subscribe((state) => { console.log(state) })

function DashboardContainer() {
  const history = useHistory();

  const [confirmed, setConfirmed] = useState(0);
  const [deceased, setDeceased] = useState(0);
  const [recovered, setRecovered] = useState(0);
  const [tested, setTested] = useState(0);

  const [entityDetailedData, setEntityDetailedData] = useState({});

  const [allStates, setAllStates] = useState([]);
  const [allDistricts, setAllDistricts] = useState([]);
  const [selectedState, setSelectedState] = useState(0);
  const [selectedDistrict, setSelectedDistrict] = useState(0);
  const [selectedDate, setSelectedDate] = useState(new Date());

  const [minDate, setMinDate] = useState(new Date());
  const [maxDate, setMaxDate] = useState(new Date());

  const confirmedTileData = {
    name: "confirmed",
    count: confirmed
  }

  const deceasedTileData = {
    name: "deceased",
    count: deceased
  }

  const recoveredTileData = {
    name: "recovered",
    count: recovered
  }

  const testedTileData = {
    name: "tested",
    count: tested
  }

  const handleDateChange = (date) => {
    console.log("Updating current date to :" + normalizeDate(date))
    setSelectedDate(date)
  }

  useEffect(() => {
    let token = store.getState().auth.token;
    if (token === '') {
      console.log("No token found")
      history.push("/login")
    }
    console.log("token found : " + token)
    const config = {
      headers: {
        "Authorization": "Bearer " + token
      }
    }
    INTERNAL_API.get('/state/', config).then(res => {
      if (res.status === 200) {
        console.log("GET state: " + JSON.stringify(res.data))
        let localAllState = []
        res.data.map(state => localAllState.push(state))
        setAllStates(localAllState)
      }
    }).catch(
      err => {
        console.error("GET state data call failed, retry again" + err)
        history.push("/login")
      }
    )
    INTERNAL_API.get('/daterange', config).then(res => {
      if (res.status === 200) {
        console.log("GET date : " + res.data.maxDate + "   " + res.data.minDate)
        setMaxDate(new Date(res.data.maxDate))
        setMinDate(new Date(res.data.minDate))
        handleDateChange(new Date(res.data.maxDate))
        console.log("Updated date range from max: " + new Date(res.data.maxDate) + "  to min:" + new Date(res.data.minDate))
      }
    }).catch(
      err => {
        console.error("GET date range call failed, retry again" + err)
        history.push("/login")
      }
    )
  }, [])

  const handleStateSelect = (event) => {
    console.log("Select state called")
    setSelectedState(event.target.value)
    let token = store.getState().auth.token;
    if (token === '') {
      history.push("/login")
    }
    const config = {
      headers: {
        "Authorization": "Bearer " + token
      }
    }
    INTERNAL_API.get('/district/?stateid=' + event.target.value, config).then(res => {
      if (res.status === 200) {
        console.log("GET district names: " + JSON.stringify(res.data))
        let localAllDistrict = []
        res.data.map(district => localAllDistrict.push(district))
        setAllDistricts(localAllDistrict)
      }
    }).catch(
      err => {
        console.error("GET all district data call failed, retry again" + err)
        history.push("/login")
      }
    )
    INTERNAL_API.get('/state/' + event.target.value + "?start=" + normalizeDate(selectedDate) + "&end=" + normalizeDate(selectedDate), config).then(res => {
      if (res.status === 200) {
        console.log("GET state: " + JSON.stringify(res.data))
        setConfirmed(res.data.counts[0].confirmed || 0)
        setDeceased(res.data.counts[0].deceased || 0)
        setRecovered(res.data.counts[0].recovered || 0)
        setTested(res.data.counts[0].tested || 0)
      }
    }).catch(
      err => {
        console.error("GET state data call failed, retry again" + err)
        history.push("/login")
      }
    )
    INTERNAL_API.get('/state/' + event.target.value + "?start=" + normalizeDate(minDate) + "&end=" + normalizeDate(maxDate), config)
      .then(res => {
        if (res.status === 200) {
          console.log("GET state detailed: " + JSON.stringify(res.data))
          setEntityDetailedData(res.data)
        }
      }).catch(
        err => {
          console.error("GET state data call failed, retry again" + err)
          history.push("/login")
        }
      )
  }

  const handleDistrictSelect = (event) => {
    console.log("Select district called")
    setSelectedDistrict(event.target.value)
    let token = store.getState().auth.token;
    if (token === '') {
      history.push("/login")
    }
    const config = {
      headers: {
        "Authorization": "Bearer " + token
      }
    }
    INTERNAL_API.get('/district/' + event.target.value + "?start=" + normalizeDate(selectedDate) + "&end=" + normalizeDate(selectedDate), config).then(res => {
      if (res.status === 200) {
        console.log("GET state: " + res.data || 'N/A')
        setConfirmed(res.data.counts[0].confirmed || 'N/A')
        setDeceased(res.data.counts[0].deceased || 'N/A')
        setRecovered(res.data.counts[0].recovered || 'N/A')
        setTested(res.data.counts[0].tested || 'N/A')
      }
    }).catch(
      err => {
        console.error("GET districts data call failed, retry again" + err)
        history.push("/login")
      }
    )
    INTERNAL_API.get('/district/' + event.target.value + "?start=" + normalizeDate(minDate) + "&end=" + normalizeDate(maxDate), config)
      .then(res => {
        if (res.status === 200) {
          console.log("GET district detailed: " + JSON.stringify(res.data))
          setEntityDetailedData(res.data)
        }
      }).catch(
        err => {
          console.error("GET district data call failed, retry again" + err)
          history.push("/login")
        }
      )
  }

  const normalizeDate = (date) => {
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + (date.getDate())
  }

  return (
    <React.Fragment>
      <div id='topPaddingDashboard'></div>
      <Grid container >
        <Grid container item justify="flex-start" spacing={2}>
          <Grid item>
            <MuiPickersUtilsProvider utils={DateFnsUtils} >
              <KeyboardDatePicker disableToolbar variant="inline" format="yyyy-MM-dd" autoOk={true}
                label="Select Date" value={selectedDate} onChange={handleDateChange}
                maxDate={maxDate} minDate={minDate} />
            </MuiPickersUtilsProvider>
          </Grid>
          <Grid item><ChevronRightIcon /></Grid>
          <Grid item>
            <FormControl variant="outlined" className="stateSelect">
              <InputLabel>State</InputLabel>
              <Select value={selectedState} onChange={handleStateSelect} label="State" style={{minWidth: 80}}>
                {allStates.map((state) => <MenuItem value={state.id}>{state.name}</MenuItem>)}
              </Select>
            </FormControl>
          </Grid>
          <Grid item><ChevronRightIcon /></Grid>
          <Grid item>
            <FormControl variant="outlined" className="districtSelect">
              <InputLabel>District</InputLabel>
              <Select value={selectedDistrict} onChange={handleDistrictSelect} label="District" style={{minWidth: 160}}>
                {allDistricts.map((district) => <MenuItem value={district.id}>{district.name}</MenuItem>)}
              </Select>
            </FormControl>
          </Grid>
        </Grid>

        <Grid container item justify="space-evenly">
          <Grid item>
            <InfoTile data={confirmedTileData} />
          </Grid>
          <Grid item>
            <InfoTile data={deceasedTileData} />
          </Grid>
          <Grid item>
            <InfoTile data={recoveredTileData} />
          </Grid>
          <Grid item>
            <InfoTile data={testedTileData} />
          </Grid>
        </Grid>
        <Grid container item justify="space-evenly">
          <DatewiseGraph entity={entityDetailedData} />
        </Grid>
      </Grid>
    </React.Fragment >
  );
}

export default DashboardContainer;