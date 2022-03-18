import React from "react";
import { PlayerStats, MoveTimeFromAverageCount } from "../models/player-stats";

export interface StatsChartProps {
  playerStats: PlayerStats
  timeControl: string,
}

export class StatsChart extends React.Component<StatsChartProps, {}> {


  private getChartData = () => {
    const { playerStats, timeControl } = this.props;

    const averageMoveTimeRanges: MoveTimeFromAverageCount = playerStats.gameStatsByTimeControl[timeControl].moveTimeRangeFromAverage;

    const data = {
        labels: Object.keys(averageMoveTimeRanges),
        datasets: [
          {
            label: 'Time control',
            data: Object.values(averageMoveTimeRanges),
          }

        ]
    };
        
    console.log(data);
    return data;
  }
}