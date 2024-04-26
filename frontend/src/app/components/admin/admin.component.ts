import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { UserCrudComponent } from './user-crud/user-crud.component';
import { CategoryCrudComponent } from './category-crud/category-crud.component';
import { ProductCrudComponent } from './product-crud/product-crud.component';
import { ButtonsCrudComponent } from '../buttons/buttons-crud/buttons-crud.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    RouterOutlet,
    UserCrudComponent,
    CategoryCrudComponent,
    ProductCrudComponent,
    RouterLink,
    ButtonsCrudComponent,
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {

}
