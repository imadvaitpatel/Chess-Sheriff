import React from 'react';
import '../../css/date-dropdown.css';

type DateDropdownProps = {
  onChange: (e: any) => void;
}

export const DateDropdown = (props: DateDropdownProps) => {;

    return (
      <select className='date-dropdown' onChange={(e) => props.onChange(e.target.value)}>
        {getDateOptions()}
      </select>
    );
}

const getDateOptions = (): [JSX.Element] => {
  let options: any = [];
  for(const numMonths of [1, 2, 3, 6, 9, 12]) {
    options.push(
      <option value ={numMonths} key={numMonths}>
        Past {numMonths} {numMonths === 1 ? 'month' : 'months'} 
      </option>
    );
  }
  return options;
}
