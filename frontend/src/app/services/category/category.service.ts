import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoryInterface } from '../../interfaces/category';
import { Observable } from 'rxjs';
import { Page } from '../../interfaces/page';
import { app } from '../../../../server';
import { AppConstants } from '../../app-constants';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private categories = 'categories';

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Page<CategoryInterface>> {
    return this.http.get<Page<CategoryInterface>>(AppConstants.urlBackEnd + this.categories)
  }

  insertCategory(category: any) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post(AppConstants.urlBackEnd + this.categories, category, { headers });
  }
}
