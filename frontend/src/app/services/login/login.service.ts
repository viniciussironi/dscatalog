import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../../app-constants';
import { error } from 'console';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private oauth = 'oauth2/token';

  constructor(private http: HttpClient) { }
  
  getLogin(username: string, password: string) {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa('myclientid:myclientsecret')
    })
  
    return this.http.post(AppConstants.urlBackEnd + this.oauth, body, { headers });
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('access_token');
    const tokenExpiresIn = localStorage.getItem('expires_in');
    const tokenIssuedAt = localStorage.getItem('issued_at');
    const dateNow = Date.now();
    const expiryDate = Number(tokenIssuedAt) + Number(tokenExpiresIn)

    if(dateNow > expiryDate) {
      return false;
    }

    if(token === '') {
      return false;
    }

    return true;
  }

  getRecoverToken(username: string) {
    const body = {
      email: username
    }
    return this.http.post(AppConstants.urlBackEnd + 'auth/recover-token', body).subscribe()
  }
}