export interface Product {
    id?: number;
    name: string;
    date: string;
    price: number;
    description: string;
    imgUrl: string;
    categories?: [{id?: number, name?:string}];
}