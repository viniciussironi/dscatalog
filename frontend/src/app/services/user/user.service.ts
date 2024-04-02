import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../../app-constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private users = 'users';

  constructor(private http: HttpClient) { }

  insertUser(user: any) {
    const headers = new HttpHeaders ({
      //'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    });

    return this.http.post(AppConstants.urlBackEnd + this.users, user, { headers });
  }
}
