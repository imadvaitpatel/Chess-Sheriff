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
                  color: '#fff',
                  font: {
                    size: 18
                  }
                },
                tooltip: {
                  enabled: false
                },
                legend: {
                  // do nothing on click
                  onClick: function(event, legendItem) {},
                  labels: {
                    color: 'white'
                  }
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
                    color: '#fff',
                  },
                  title: {
                    text: 'Time from average move time',
                    color: '#fff',
                    display: true
                  }
                },
                y: {
                  stacked: true,
                  ticks: {
                    color: '#fff',
                  },
                  title: {
                    text: 'Number of moves',
                    color: '#fff',
                    display: true
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
    const timeControlLabels: string[] = Object.keys(averageMoveTimeRanges);

    if (timeControlLabels.indexOf('Infinity') !== -1)
    {
      const maxTimeControl: string = timeControlLabels.reduce((a, b) => {
        if (isNaN(parseInt(a)))
        {
          return b;
        }
        if (isNaN(parseInt(b)))
        {
          return a;
        }
        return parseInt(a) > parseInt(b) ? a : b;
      });

      timeControlLabels[timeControlLabels.indexOf('Infinity')] = maxTimeControl.concat('+');
    }

    const data = {
        labels: timeControlLabels,
        datasets: [
          {
            label: 'Number of moves made within the average move time',
            data: Object.values(averageMoveTimeRanges),
            backgroundColor: '#ab5265'
          }

        ]
    };
        
    return data;
  }
}