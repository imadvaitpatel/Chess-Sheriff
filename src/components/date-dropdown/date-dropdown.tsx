import React from 'react';
import '../../css/date-dropdown.css';
import { SearchDateRange } from '../../util/date-util';

type DateDropdownProps = {
  onChange: (e: any) => void;
}

type DateDropDownState = {
  selectedOption: SearchDateRange;
}

export class DateDropdown extends React.Component<DateDropdownProps, DateDropDownState> {

  constructor(props: any) {
    super(props);
    this.state ={
      selectedOption: SearchDateRange.ONE_MONTH
    };
  }

  render() {
    return (
      <select className='date-dropdown' value={this.state.selectedOption} onChange={(e) => this.props.onChange(e.target.value)}>
        {this.getDateOptions()}
      </select>
    );
  }

  private getDateOptions = (): [JSX.Element] => {
    let options: any = [];
    for(const dateRange of Object.values(SearchDateRange)) {
      options.push(
        <option>
          Past {dateRange}
        </option>
      );
    }
    return options;
  }
}

