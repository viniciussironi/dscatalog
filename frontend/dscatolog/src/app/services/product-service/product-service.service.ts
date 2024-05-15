import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProductInterface } from '../../interface/product-interface';
import { Page } from '../../interface/pageable-interface';
import { Observable } from 'rxjs';
import { AppConstants } from '../../app-constants';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private products = 'products';

  constructor(private http: HttpClient) {}

  getProducts(pageNumber: string, productName: string, categoryId: string, size: string): Observable<Page<ProductInterface>> {
    let params = new HttpParams()
    .set('page', pageNumber)
    .set('size', size)
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

  updateProduct(product: any, id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.put(AppConstants.urlBackEnd + this.products + '/' + id, product, { headers });
  }

  deleteProduct(id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });
    return this.http.delete(AppConstants.urlBackEnd + this.products + '/' + id, { headers })
  }
}
