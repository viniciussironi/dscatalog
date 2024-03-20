import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoryInterface } from '../../interfaces/category';
import { Observable } from 'rxjs';
import { Page } from '../../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private url = "http://localhost:8080/categories";

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Page<CategoryInterface>> {
    return this.http.get<Page<CategoryInterface>>(this.url)
  }
}
