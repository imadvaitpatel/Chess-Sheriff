import React from 'react';
import '../../css/date-dropdown.css';
import { SearchDateRange } from '../../util/date-util';

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
  for(const dateRange of Object.values(SearchDateRange)) {
    options.push(
      <option>
        {dateRange}
      </option>
    );
  }
  return options;
}
