import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductInterface } from '../interfaces/products';

@Injectable({
  providedIn: 'root'
})

export class ProductsService {
  private urlBackend = "http://localhost:8080/";


  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<ProductInterface> {
    return this.http.get<ProductInterface>(this.urlBackend+"products/1");
  }
}
