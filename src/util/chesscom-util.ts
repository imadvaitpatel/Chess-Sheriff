const BASE_CHESSCOM_PUB_URL= 'https://api.chess.com/pub';
export const CHESSCOM_MAX_CHARACTERS = 20;

export function getPlayerArchives(player: string): string {
  return `${BASE_CHESSCOM_PUB_URL}/player/${player}/games/archives`;
}
