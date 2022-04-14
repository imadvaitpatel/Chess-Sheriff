import React from 'react';
import { PlayerStats } from '../../models/player-stats';
import '../../css/cheat-report.css';
import { StatsChart } from './stats-chart';
import { OverallStats } from './overall-stats';

type CheatReportProps = {
  playerStats: PlayerStats
}

type CheatReportState = {
  selectedTimeControl?: string
}

export class CheatReport extends React.Component<CheatReportProps, CheatReportState> {

  constructor(props: CheatReportProps) {
    super(props);
    this.state = {
      selectedTimeControl: undefined,
    }
  }

  render() {

    const { playerStats } = this.props;

    if (Object.keys(playerStats.gameStatsByTimeControl).length < 1) {
      return (
        <div className='not-found-message'>
          No games found for this user
        </div>
      );
    }

    return (
      <div className='stats-container'>
        <OverallStats
          playerStats={playerStats}
        />

        <div className='time-control-dropdown'>
          <label htmlFor='time-control-dropdown'>Time Control:&emsp;</label>
          <select defaultValue='DEFAULT' onChange={e => this.setSelectedTimeControl(e.target.value)}>
            <option hidden disabled value={'DEFAULT'}> -- select an option -- </option>
            {Object.keys(playerStats.gameStatsByTimeControl).map((timeControl, index) => {
              return <option value={timeControl} key={index}>{timeControl}</option>
            })}
          </select>
      </div>

      
      {this.state.selectedTimeControl !== undefined 
            ? <StatsChart
              playerStats={playerStats}
              timeControl={this.state.selectedTimeControl} />
            : null}
      </div>
    );

    // TODO: add avg move time so its obvious what the data in the chart is for 
    // TODO: when a new user is searched for, reset state and add loading indicator
  }

  private setSelectedTimeControl = (timeControl : string): void => {
    this.setState({
      selectedTimeControl: timeControl
    });
  }

}