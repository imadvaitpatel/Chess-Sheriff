import React from 'react';
import '../../css/search-bar-container.css';
import { DateDropdown } from '../date-dropdown/date-dropdown';
import { SearchBar } from './search-bar';
import { SearchButton } from './search-button';

type SearchBarContainerProps = {
  onSearchBarUpdate: (value: string) => void;
  onDateDropdownUpdate: (value: string) => void;
  isCreatingReport: boolean;
  searchValue: string;
  handleSearchClick: () => void;
}

export class SearchBarContainer extends React.Component<SearchBarContainerProps, {}> {

  constructor(props: any) {
    super(props);
    this.updateSearchValue = this.updateSearchValue.bind(this);
  }

  render() {
    return (
      <>
        <img className='stockfish-img' src='stockfish.png' alt='stockfish.png'/>
        <div className='search-bar-container'>
          <SearchBar
            value={this.props.searchValue}
            onUpdate={this.updateSearchValue}
          />
          <SearchButton 
            isDisabled={this.props.isCreatingReport || this.props.searchValue === ''}
            onClick={this.props.handleSearchClick}
          />
           <DateDropdown
            onChange={this.props.onDateDropdownUpdate}
           />
        </div>
      </>
    )
  }

  private updateSearchValue(event: any) {
    const newValue = event.target.value;
    this.props.onSearchBarUpdate(newValue);
  }
}