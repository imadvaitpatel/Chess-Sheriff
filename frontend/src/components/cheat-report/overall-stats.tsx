import React from "react";
import { PlayerStats } from "../../models/player-stats";
import '../../css/overall-stats.css';

export interface OverallStatsProps {
  playerStats: PlayerStats,
}

export class OverallStats extends React.Component<OverallStatsProps, {}> {
  
  render () {
    const { playerStats } = this.props;
    return (
      <div className='overall-stats-container'>
        <div className='overall-stats-title'>
          Overall Stats
        </div>
        <div className='overall-stats-columns'>
          <div>
            Total Games: {playerStats.totalGames}
          </div>
          <div>
            Win Rate: {100 * playerStats.overallScore / playerStats.totalGames}%
          </div>
          <div>
            CAPS Games: {playerStats.totalCapsGames}
          </div>
          <div>
            Average CAPS Score: {playerStats.overallAverageCapsScore}
          </div>
          <div>
            Lowest CAPS Score: {playerStats.overallLowestCapsScore}
          </div>
          <div>
            Highest CAPS Score: {playerStats.overallHighestCapsScore}
          </div>
      </div>
    </div>
      
    );
  }
}