import { NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonPrincipalComponent } from '../buttons/button-principal/button-principal.component';
import { LoginService } from '../../services/login/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgOptimizedImage, ButtonPrincipalComponent, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  
  username = new FormControl;
  password = new FormControl;

  constructor(private loginService: LoginService, private router: Router) {}

  getLogin() {
    this.loginService.getLogin(this.username.value, this.password.value)
    this.router.navigate(['./admin/product']);
  }
}