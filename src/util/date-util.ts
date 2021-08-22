import { isNumeric } from "./string-util";

export enum SearchDateRange {
  ONE_MONTH = 'Past 1 month', 
  THREE_MONTHS = 'Past 3 months',
  SIX_MONTHS = 'Past 6 months',
  ONE_YEAR = 'Past 1 year',
  THREE_YEARS = 'Past 3 years',
  FIVE_YEARS = 'Past 5 years'
};

export function dateRangeToMonths(dateRange: SearchDateRange): number {
  switch(dateRange) {
    case SearchDateRange.ONE_MONTH:
      return 1;
    case SearchDateRange.THREE_MONTHS:
      return 3;
    case SearchDateRange.SIX_MONTHS:
      return 6;
    case SearchDateRange.ONE_YEAR:
      return 12;
    case SearchDateRange.THREE_YEARS:
      return 36;
    case SearchDateRange.FIVE_YEARS:
      return 60;
  }
}

export const getStartDate = (dateRange: SearchDateRange): Date => {
  let date: Date = new Date();
  date.setMonth(date.getMonth() - dateRangeToMonths(dateRange));
  return date;
}

// All date strings use format YYYY/MM for easy comparison with archive dates
// January is 01 (not 00)

export const dateToArchiveFormatString = (date: Date): string => {
  const year = date.getFullYear();
  // since January is '0' in JavaScript...
  let month = date.getMonth() + 1;

  return (month < 10) ? `${year}/0${month}` : `${year}/${month}`;
}

export const compareArchiveDates = (dateOne: string, dateTwo: string): number => {
  if (dateOne.length !== dateTwo.length) { 
    return (dateOne.length > dateTwo.length ? 1 : -1);
   }
  for (let i = 0; i < dateOne.length; i++) {
    if (dateOne[i] === dateTwo[i]) {
      continue;
    }
    else if (isNumeric(dateOne[i]) && isNumeric(dateTwo[i])) {
      const numOne: number = Number(dateOne[i]);
      const numTwo: number = Number(dateTwo[i]);

      if (numOne < numTwo) {
        return -1;
      }
      else if (numOne > numTwo) {
        return 1;
      }
    }
  }
  return 0;
}

