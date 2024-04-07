import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../../app-constants';

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
  
    return this.http.post(AppConstants.urlBackEnd + this.oauth, body, { headers }).subscribe((response: any) => {
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('expires_in', response.expires_in);
        localStorage.setItem('issued_at', Date.now().toString());
      }); 
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
}