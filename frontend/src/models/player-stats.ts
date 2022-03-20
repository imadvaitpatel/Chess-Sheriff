export interface PlayerStats {
  totalGames: number
  overallScore: number
  overallLowestCapsScore: number
  overallAverageCapsScore: number
  overallHighestCapsScore: number
  totalCapsGames: number
  gameStatsByTimeControl: TimeControlToGameSetStats
}

export interface TimeControlToGameSetStats {
  [timeControl: string]: GameSetStats
}

export interface GameSetStats {
  numGames: number
  totalScore: number
  capsScores: number[]
  moveTimeRangeFromAverage: MoveTimeFromAverageCount
}

export interface MoveTimeFromAverageCount {
  [moveTimeFromAverage: number]: number
}