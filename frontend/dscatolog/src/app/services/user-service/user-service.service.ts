import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../../app-constants';
import { Observable } from 'rxjs';
import { UserInterface } from '../../interface/user-interface';
import { Page } from '../../interface/pageable-interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private users = 'users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<Page<UserInterface>> {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.get<Page<UserInterface>>(AppConstants.urlBackEnd + this.users, { headers })
  }

  insertUser(user: any) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post(AppConstants.urlBackEnd + this.users, user, { headers });
  }

  updateUser(user: any, id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post(AppConstants.urlBackEnd + this.users + '/' + id, user, { headers });
  }

  deleteUser(id: number) {
    const headers = new HttpHeaders ({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });
    return this.http.delete(AppConstants.urlBackEnd + this.users + '/' + id, { headers })
  }
}
