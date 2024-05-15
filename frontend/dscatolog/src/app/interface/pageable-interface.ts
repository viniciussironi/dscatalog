export interface Page<T> {
  content : T[];
  totalPages: number;
  number: number;
}
