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

export const convertDateToArchiveFormatString = (date: Date): string => {
  const year = date.getFullYear();
  // since January is '0' in JavaScript...
  let month = date.getMonth() + 1;

  return (month < 10) ? `${year}/0${month}` : `${year}/${month}`;
}

