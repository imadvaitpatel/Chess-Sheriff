import React from 'react';
import '../../css/search-bar.css';

const SEARCHBAR_MAX_CHARACTERS = 20;

type SearchBarProps = {
  value: string
  onUpdate: (e: any) => void
}

export const SearchBar = (props: SearchBarProps) => {
  const { value, onUpdate } = props;
  return (
    <input className='search-bar' value={value} onChange={onUpdate} placeholder='Enter username' maxLength={SEARCHBAR_MAX_CHARACTERS}/>
  ); 
}