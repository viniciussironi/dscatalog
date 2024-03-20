import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductInterface } from '../../interfaces/products';
import { Page } from '../../interfaces/page';

@Injectable({
  providedIn: 'root'
})

export class ProductService {
  private url = "http://localhost:8080/products";


  constructor(private http: HttpClient) {}
  headers = new HttpHeaders();
  params!: [
    page: string,
    categoryName: string,
    productName: string
  ]
  getProducts(pageNumber: number): Observable<Page<ProductInterface>> {
    return this.http.get<Page<ProductInterface>>(this.url+"?page="+pageNumber)
  }
}
