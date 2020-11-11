import React from 'react';
import { LineChart } from 'react-chartkick'
import './DatewiseGraph.css';
import 'chart.js'
import { Grid } from '@material-ui/core'

const DatewiseGraph = ({ entity }) => {
  const getConfirmedData = (entity) => {
    let overallData = []
    if (entity !== undefined) {
      let confirmed = {}
      confirmed.name = "Confirmed"
      confirmed.data = {}
      entity?.counts?.map(count => (
        confirmed.data[count.date] = count.confirmed
      ))
      overallData.push(confirmed)
    }
    return overallData
  }

  const getDeceasedData = (entity) => {
    let overallData = []
    if (entity !== undefined) {
      let deceased = {}
      deceased.name = "Deceased"
      deceased.data = {}
      entity?.counts?.map(count => (
        deceased.data[count.date] = count.deceased
      ))
      overallData.push(deceased)
    }
    return overallData
  }

  const getRecoveredData = (entity) => {
    let overallData = []
    if (entity !== undefined) {
      let recovered = {}
      recovered.name = "Recovered"
      recovered.data = {}
      entity?.counts?.map(count => (
        recovered.data[count.date] = count.recovered
      ))
      overallData.push(recovered)
    }
    return overallData
  }

  const getTestedData = (entity) => {
    let overallData = []
    if (entity !== undefined) {
      let tested = {}
      tested.name = "Tested"
      tested.data = {}
      entity?.counts?.map(count => (
        tested.data[count.date] = count.tested
      ))
      overallData.push(tested)
    }
    return overallData
  }
  return (
    <React.Fragment>
      <div id='topPaddingGraphs'></div>
      <Grid item container spacing={4}>
        <Grid item>
          <LineChart id="covid-confirmed-chart" data={getConfirmedData(entity)} download={true}
            xtitle="Date" ytitle="Number of cases" legend="bottom" colors={["blue"]} />
        </Grid>
        <Grid item>
          <LineChart id="covid-deceased-chart" data={getDeceasedData(entity)} download={true}
            xtitle="Date" ytitle="Number of cases" legend="bottom" colors={["red"]} />
        </Grid>
        <Grid item>
          <LineChart id="covid-recovered-chart" data={getRecoveredData(entity)} download={true}
            xtitle="Date" ytitle="Number of cases" legend="bottom" colors={["green"]} />
        </Grid>
        <Grid item>
          <LineChart id="covid-tested-chart" data={getTestedData(entity)} download={true}
            xtitle="Date" ytitle="Number of cases" legend="bottom" colors={["black"]} />
        </Grid>
      </Grid>
    </React.Fragment>
  );
}

export default DatewiseGraph;