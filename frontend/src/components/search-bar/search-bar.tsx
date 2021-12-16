import React from 'react';
import '../../css/search-bar.css';
import { CHESSCOM_MAX_CHARACTERS } from '../../util/chesscom-util';

type SearchBarProps = {
  value: string
  onUpdate: (e: any) => void
}

export const SearchBar = (props: SearchBarProps) => {
  const { value, onUpdate } = props;
  return (
    <input className='search-bar' value={value} onChange={onUpdate} placeholder='Enter username' maxLength={CHESSCOM_MAX_CHARACTERS}/>
  ); 
}