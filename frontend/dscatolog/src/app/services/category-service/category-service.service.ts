import { Injectable } from '@angular/core';
import { CategoryInterface } from '../../interface/category';
import { Page } from '../../interface/pageable-interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  updateCategory(category: any, id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.put(AppConstants.urlBackEnd + this.categories + '/' + id, category, { headers });
  }

  deleteCategory(id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });
    return this.http.delete(AppConstants.urlBackEnd + this.categories + '/' + id, { headers })
  }
}
