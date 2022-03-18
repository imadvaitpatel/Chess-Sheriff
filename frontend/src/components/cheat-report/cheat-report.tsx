import React from 'react';
import { MoveTimeFromAverageCount, PlayerStats, TimeControlToGameSetStats } from '../models/player-stats';
import { Bar } from 'react-chartjs-2';
import '../../css/cheat-report.css';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { StatsChart } from './stats-chart';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

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

    else if (this.state.selectedTimeControl === undefined) {
      return ( //TODO: add selection for time control
        <>
  
        </>
      );
    }

    return (
        <StatsChart
          playerStats={playerStats}
          timeControl={this.state.selectedTimeControl}
        />
        // TODO: make this look better later and use options maybe
    );
  }

  private getTimeControls = (): string[] => {
    return Object.keys(this.props.playerStats.gameStatsByTimeControl);
  }

}