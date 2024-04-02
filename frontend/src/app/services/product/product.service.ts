import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductInterface } from '../../interfaces/products';
import { Page } from '../../interfaces/page';
import { ProductInsertInterface } from '../../interfaces/product-insert';
import { AppConstants } from '../../app-constants';

@Injectable({
  providedIn: 'root'
})

export class ProductService {
  private products = 'products';

  constructor(private http: HttpClient) {}
  
  getProducts(pageNumber: string, productName: string, categoryId: string): Observable<Page<ProductInterface>> {
    let params = new HttpParams()
    .set('page', pageNumber)
    .set('name', productName)
    .set('categoryId', categoryId);
    
    return this.http.get<Page<ProductInterface>>(AppConstants.urlBackEnd + this.products, {params})
  }

  getProductById(id: number): Observable<ProductInterface> {
    return this.http.get<ProductInterface>(`${AppConstants.urlBackEnd + this.products}/${id}`)
  }

  insertProduct(product: any) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post(AppConstants.urlBackEnd + this.products, product, { headers });
  }  
}
