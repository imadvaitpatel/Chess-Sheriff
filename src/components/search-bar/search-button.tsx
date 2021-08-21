import React from 'react';
import '../../css/search-button.css';

type SearchButtonProps = {
  isCreatingReport: boolean,
  onClick: any 
} 

export class SearchButton extends React.Component<SearchButtonProps, {}> {
  
  render() {
    const { isCreatingReport, onClick } = this.props;
    return (
      <button className='search-button' disabled={isCreatingReport} onClick={onClick}>
        Search
      </button>
    )
  }
}