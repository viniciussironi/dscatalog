import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonPrincipalComponent } from '../buttons/button-principal/button-principal.component';
import { NgOptimizedImage } from '@angular/common';
import { LoginService } from '../../services/login-service/login-service.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgOptimizedImage,
    ButtonPrincipalComponent,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.sass'
})

export class LoginComponent {

  username = new FormControl;
  password = new FormControl;

  errorMessage: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  getLogin() {
    this.loginService.getLogin(this.username.value, this.password.value).subscribe((response: any) => {
      localStorage.setItem('access_token', response.access_token);
      localStorage.setItem('expires_in', response.expires_in);
      localStorage.setItem('issued_at', Date.now().toString());
      this.router.navigate(['./admin/product']);
    },
      (error: any) => {
      this.errorMessage = error.error.error
    });
  }
}
