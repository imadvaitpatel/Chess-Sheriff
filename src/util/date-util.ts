export enum SearchDateRange {
  ONE_MONTH = '1 month', 
  THREE_MONTHS = '3 months',
  SIX_MONTHS = '6 months',
  ONE_YEAR = '1 year',
  THREE_YEARS = '3 years',
  FIVE_YEARS = '5 years'
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
  let month = date.getMonth();

  return (month < 10) ? `${year}/0${month}` : `${year}/${month}`;
}

