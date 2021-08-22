import { compareArchiveDates } from "./date-util";

const BASE_CHESSCOM_PUB_URL= 'https://api.chess.com/pub';
export const CHESSCOM_MAX_CHARACTERS = 20;

export function getPlayerArchives(player: string): string {
  return `${BASE_CHESSCOM_PUB_URL}/player/${player}/games/archives`;
}

export function getGamesFromArchives(archives: [string], startDateString: string) {
  for (let i = archives.length - 1; i >= 0; i--) {
    const archive = archives[i];
    const archiveDate = archive.substring(archive.length - 7);
    const result = compareArchiveDates(archiveDate, startDateString);

    if (result >= 0) {
      // fetch games and do stuff
    }

    console.log(archive);

    if (result <= 0) {
      break;
    }
  }
}