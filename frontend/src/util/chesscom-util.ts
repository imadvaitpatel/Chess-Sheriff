import { compareArchiveDates } from "./date-util";

const BASE_CHESSCOM_PUB_URL= 'https://api.chess.com/pub';
export const CHESSCOM_MAX_CHARACTERS = 20;

export function getArchivesUrl(player: string): string {
  return `${BASE_CHESSCOM_PUB_URL}/player/${player}/games/archives`;
}

export function getArchivesAfterDate(archives: [string], dateString: string): string[] {
  const archivesInRange: string[] = [];
  for (let i = archives.length - 1; i >= 0; i--) {
    const archive = archives[i];
    const archiveDate = archive.substring(archive.length - 7);
    const result = compareArchiveDates(archiveDate, dateString);

    if (result >= 0) {
      archivesInRange.push(archive);
    }
    if (result <= 0) {
      break;
    }
  }
  return archivesInRange;
}