import React from 'react';
import '../../css/search-bar-container.css';
import { DateDropdown } from './date-dropdown';
import { SearchButton } from './search-button';

const SEARCHBAR_MAX_CHARACTERS = 20;

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
        <div className='search-bar-container'>
          <div className='search-box'>
            <input 
              className='search-input' 
              value={this.props.searchValue} 
              onChange={this.updateSearchValue} 
              placeholder='Enter username' 
              maxLength={SEARCHBAR_MAX_CHARACTERS}
            />
            <SearchButton 
            isDisabled={this.props.isCreatingReport || this.props.searchValue === ''}
            onClick={this.props.handleSearchClick}
            />
          </div>
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