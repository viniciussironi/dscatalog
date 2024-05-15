export interface ProductInterface{
  id: number;
  name: string;
  categories: [{id: number, name: string}];
  price: number;
  description: string;
  imgUrl: string;
}
