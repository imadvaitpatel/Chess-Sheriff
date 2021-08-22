import React from 'react';
import '../../css/master-component.css';
import { SearchBarContainer } from '../search-bar/search-bar-container';
import { convertDateToArchiveFormatString, getStartDate, SearchDateRange } from '../../util/date-util';
import { getPlayerArchives } from '../../util/chesscom-util';

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
    // make api calls and backend stuff

    const response = await fetch(getPlayerArchives(this.state.currentUsername));
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
      const startDate = convertDateToArchiveFormatString(getStartDate(this.state.selectedDateRange));
      console.log(startDate);
    }
    
  }

  private updateUsername(newUsername: string) {
    this.setState({
      currentUsername: newUsername,
      showUserErrorMessage: false
    });
  }

  private updateDateRange(newDateRange: SearchDateRange) {
    this.setState({
      selectedDateRange: newDateRange
    });
  }
}


