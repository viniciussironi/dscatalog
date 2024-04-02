import { NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import { ButtonPrincipalComponent } from '../buttons/button-principal/button-principal.component';
import { LoginService } from '../../services/login/login.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { LoginInterface } from '../../interfaces/login';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgOptimizedImage, ButtonPrincipalComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  
  username = new FormControl;
  password = new FormControl;

  constructor(private loginService: LoginService) {}

  getLogin() {
    this.loginService.getLogin(this.username.value, this.password.value)
  }
}
