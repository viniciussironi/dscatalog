import { Component } from '@angular/core';
import { ButtonRecoverComponent } from '../../../buttons/button-recover/button-recover.component';



@Component({
  selector: 'app-form-new-password',
  standalone: true,
  imports: [ButtonRecoverComponent],
  templateUrl: './form-new-password.component.html',
  styleUrl: './form-new-password.component.css'
})
export class FormNewPasswordComponent {

}
