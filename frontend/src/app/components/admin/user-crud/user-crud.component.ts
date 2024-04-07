import { Component } from '@angular/core';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { UserService } from '../../../services/user/user.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-crud',
  standalone: true,
  imports: [ButtonsCrudComponent, ReactiveFormsModule],
  templateUrl: './user-crud.component.html',
  styleUrl: './user-crud.component.css'
})
export class UserCrudComponent {
  
  firstName = new FormControl;
  lastName = new FormControl;
  email = new FormControl;
  password = new FormControl;

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
      password: this.password.value,
    };
    
    this.userService.insertUser(user).subscribe()
  }
}
