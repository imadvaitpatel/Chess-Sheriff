import React from 'react';
import '../../css/search-button.css';

type SearchButtonProps = {
  isDisabled: boolean,
  onClick: any 
} 

export class SearchButton extends React.Component<SearchButtonProps, {}> {
  
  render() {
    const { isDisabled, onClick } = this.props;
    return (
      <button className='searchButton' type='submit' disabled={isDisabled} onClick={onClick}>
        <i className='fa fa-search'></i>
      </button>
    )
  }
}