import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ProductInterface } from '../interfaces/products';
import { catchError, retry } from 'rxjs/operators';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})

export class ProductsService {
  private url = "http://localhost:8080/products";


  constructor(private http: HttpClient) {}

  getProducts(): Observable<Page<ProductInterface>> {
    return this.http.get<Page<ProductInterface>>(this.url)
    .pipe(
      retry(2),
      catchError(this.handleError)
    )
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Erro ocorreu no lado do client
      errorMessage = error.error.message;
    } else {
      // Erro ocorreu no lado do servidor
      errorMessage = `Código do erro: ${error.status}, ` + `menssagem: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  };
}
