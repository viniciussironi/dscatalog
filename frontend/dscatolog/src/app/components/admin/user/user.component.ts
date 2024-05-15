import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { Page } from '../../../interface/pageable-interface';
import { UserInterface } from '../../../interface/user-interface';
import { UserService } from '../../../services/user-service/user-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.sass'
})
export class UserComponent implements OnInit {
  users: Page<UserInterface> = { content: [], totalPages: 0, number: 0 };
  selectedPage!: number;

  constructor(private userService: UserService, private cdr: ChangeDetectorRef, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getUsers();
    this.selectPage(0);
  }

  getUsers() {
      this.userService.getUsers().subscribe(
      (users: Page<UserInterface>) => {
      this.users = users;
    });
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe(() => {
      this.getUsers();
      this.snackBar.open('Usuário deletado com sucesso!', 'X', {duration: 5000, verticalPosition: 'top', horizontalPosition: 'center'})
    });
  }

  nextPage() {
    if (this.users.number < this.users.totalPages - 1) {
      this.users.number++;
      this.selectedPage = this.users.number;
      this.getUsers();
    }
  }

  backPage() {
    if (this.users.number > 0) {
      this.users.number --;
      this.selectedPage = this.users.number;
      this.getUsers();
    }
  }

  selectPage(page: number) {
    this.selectedPage = page;
    this.cdr.markForCheck();
  }
}
