export function isNumeric(s: string) {
  return ((s != null) && (s !== '') && !isNaN(Number(s)));
}