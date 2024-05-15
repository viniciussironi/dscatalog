import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ButtonsAdminComponent } from '../../../buttons/buttons-admin/buttons-admin.component';
import { UserService } from '../../../../services/user-service/user-service.service';

@Component({
  selector: 'app-add-user',
  standalone: true,
  imports: [
    ButtonsAdminComponent,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.sass'
})
export class AddUserComponent implements OnInit {

  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  password1 = new FormControl('', [Validators.required]);
  password2 = new FormControl('', [Validators.required]);

  constructor(private userService: UserService) { }

  ngOnInit() {
  }


  insertCategory() {
    const user = {
    firstName: this.firstName.value
    };

    this.userService.insertUser(user).subscribe()
  }
}

