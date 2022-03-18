import React from 'react';
import '../../css/master-component.css';
import { CheatReport } from '../cheat-report/cheat-report';
import { PlayerStats } from '../models/player-stats';
import { SearchBarContainer } from '../search-bar/search-bar-container';

type MasterComponentState = {
  isCreatingReport: boolean;
  currentUsername: string;
  selectedPastNumMonths: number;
  showUserErrorMessage: boolean;
  playerStats: PlayerStats | undefined;
}

export class MasterComponent extends React.Component<{}, MasterComponentState> {

  constructor(props: any) {
    super(props);
    this.state = {
      isCreatingReport: false,
      currentUsername: '',
      selectedPastNumMonths: 1,
      showUserErrorMessage: false,
      playerStats: undefined
    }
    this.handleSearchClick = this.handleSearchClick.bind(this);
    this.updateUsername = this.updateUsername.bind(this);
    this.updateDateRange = this.updateDateRange.bind(this);
  }

  render() {
    return (
      <>
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
          {this.state.playerStats !== undefined
            ? <CheatReport playerStats={this.state.playerStats}/>
            : null
          }
      </>
    );
  }

  private async handleSearchClick() {

    const response = await fetch(`http://localhost:8080/stats/${this.state.currentUsername}?pastMonths=${this.state.selectedPastNumMonths}`);
    
    if (response.status === 500) {
      this.setState({
        showUserErrorMessage: true,
        playerStats: undefined
      });
    }
    else {
      const data: PlayerStats = await response.json();

      this.setState({
          playerStats: data
        });
    } 
  }

  private updateUsername(newUsername: string): void {
    this.setState({
      currentUsername: newUsername,
      showUserErrorMessage: false
    });
  }

  private updateDateRange(newDateRange: string): void {
    this.setState({
      selectedPastNumMonths: Number(newDateRange),
      showUserErrorMessage: false
    });
  }
}


