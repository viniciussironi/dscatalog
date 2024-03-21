import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
  
  getProducts(pageNumber: string, productName: string, categoryId: string): Observable<Page<ProductInterface>> {
    let params = new HttpParams()
    .set('page', pageNumber)
    .set('name', productName)
    .set('categoryId', categoryId);
    
    return this.http.get<Page<ProductInterface>>(this.url, {params})
  }
}
