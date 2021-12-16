import React from 'react';
import '../../css/master-component.css';
import { SearchBarContainer } from '../search-bar/search-bar-container';
import { dateToArchiveFormatString, getStartDate, SearchDateRange } from '../../util/date-util';
import { getArchivesAfterDate, getArchivesUrl } from '../../util/chesscom-util';

type MasterComponentState = {
  isCreatingReport: boolean;
  currentUsername: string;
  selectedDateRange: SearchDateRange;
  showUserErrorMessage: boolean;
}

export class MasterComponent extends React.Component<{}, MasterComponentState> {

  constructor(props: any) {
    super(props);
    this.state = {
      isCreatingReport: false,
      currentUsername: '',
      selectedDateRange: SearchDateRange.ONE_MONTH,
      showUserErrorMessage: false
    }
    this.handleSearchClick = this.handleSearchClick.bind(this);
    this.updateUsername = this.updateUsername.bind(this);
    this.updateDateRange = this.updateDateRange.bind(this);
  }

  render() {
    return (
      <div>
        <div className='intro-text'>
          Did you just lose a chess game and think your opponent is a cheater? Enter their chess.com username and a date range for their games
          to find out now!
        </div>
        <SearchBarContainer 
          searchValue={this.state.currentUsername}
          isCreatingReport={this.state.isCreatingReport}
          onSearchBarUpdate={this.updateUsername}
          onDateDropdownUpdate={this.updateDateRange}
          handleSearchClick={this.handleSearchClick}
          />
          {this.state.showUserErrorMessage 
            ?<div className='user-error'>User "{this.state.currentUsername}" does not exist</div>
            : null
          }
      </div>
    );
  }

  private async handleSearchClick() {
    const response = await fetch(getArchivesUrl(this.state.currentUsername));
    const data = await response.json();

    if (response.status === 404) {
      this.setState({
        showUserErrorMessage: true
      });
    }
    else {
      console.log(data);
      //  this.setState({
      //   isCreatingReport: true
      // });
      const startDate = dateToArchiveFormatString(getStartDate(this.state.selectedDateRange));
      console.log(startDate);
      const archives = getArchivesAfterDate(data.archives, startDate);
      console.log(archives);
      // fix cors error next!!!!!!!!
      const games = await this.getGamesFromArchives(archives);
      
      await fetch(`http://localhost:8080/stats/2`);
    }
    
  }

  private updateUsername(newUsername: string): void {
    this.setState({
      currentUsername: newUsername,
      showUserErrorMessage: false
    });
  }

  private updateDateRange(newDateRange: SearchDateRange): void {
    this.setState({
      selectedDateRange: newDateRange
    });
  }

  private getGamesFromArchives = async (archives: string[]) => {
    const games: string[] = [];

    for (let i = 0; i < archives.length; i++) {
      const response = await fetch(archives[i]);
      const data = await response.json();
      console.log(data);
      
      for (const game in data.games) {
        games.push(data.games[game]);
      }
    }
    console.log(games);
    return games;
  }
}

