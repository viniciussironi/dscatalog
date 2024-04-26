import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

import { UserService } from '../../../services/user/user.service';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';

@Component({
  selector: 'app-user-crud',
  standalone: true,
  imports: [
    ButtonsCrudComponent,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './user-crud.component.html',
  styleUrl: './user-crud.component.css'
})
export class UserCrudComponent {

  firstName = new FormControl('', [Validators.required])
  lastName = new FormControl('', [Validators.required])
  email = new FormControl('', [Validators.required, Validators.email])
  password1 = new FormControl('', [Validators.required, Validators.minLength(6)]);
  password2 = new FormControl('', [Validators.required, Validators.minLength(6)]);
  constructor(private userService: UserService) { }

  onSubmit(event: Event) {
    event.preventDefault();
    this.insertUser();
  }

  insertUser() {
    const user = {
      firstName: this.firstName.value,
      lastName: this.lastName.value,
      email: this.email.value,
      // password: this.password.value,
    };

    this.userService.insertUser(user).subscribe()
  }
}
