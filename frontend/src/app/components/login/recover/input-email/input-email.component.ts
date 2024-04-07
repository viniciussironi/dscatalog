import { Component } from '@angular/core';
import { ButtonRecoverComponent } from '../../../buttons/button-recover/button-recover.component';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../../../services/login/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-input-email',
  standalone: true,
  imports: [ButtonRecoverComponent, ReactiveFormsModule],
  templateUrl: './input-email.component.html',
  styleUrl: './input-email.component.css'
})
export class InputEmailComponent {
  username = new FormControl;

  constructor(private loginService: LoginService, private router: Router) {}

  getRecoverToken() {
    this.loginService.getRecoverToken(this.username.value);
    //this.router.navigate(['./recover/message']);
  }
}