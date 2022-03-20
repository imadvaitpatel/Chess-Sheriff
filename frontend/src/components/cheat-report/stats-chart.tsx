import React from "react";
import { PlayerStats, MoveTimeFromAverageCount } from "../../models/player-stats";
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

export interface StatsChartProps {
  playerStats: PlayerStats
  timeControl: string,
}

export class StatsChart extends React.Component<StatsChartProps, {}> {

  render () {
    return (
      <>
        <Bar
          options={{
              plugins: {
                title: {
                  display: true,
                  text: 'Stats by Time Control',
                },
                tooltip: {
                  enabled: true
                }
              },
              responsive: true,
              interaction: {
                mode: 'index' as const,
                intersect: false,
              },
              scales: {
                x: {
                  stacked: true,
                  ticks: {
                    color: '#484f4f',
                  }
                },
                y: {
                  stacked: true,
                  ticks: {
                    color: '#484f4f',
                  }
                },
              },
           }}
          data={this.getChartData()}
        />
      </>
    );
  }

  private getChartData = () => {
    const { playerStats, timeControl } = this.props;

    const averageMoveTimeRanges: MoveTimeFromAverageCount = playerStats.gameStatsByTimeControl[timeControl].moveTimeRangeFromAverage;

    const data = {
        labels: Object.keys(averageMoveTimeRanges),
        datasets: [
          {
            label: 'Time control',
            data: Object.values(averageMoveTimeRanges),
            backgroundColor: '#563f46'
          }

        ]
    };
        
    console.log(data);
    return data;
  }
}