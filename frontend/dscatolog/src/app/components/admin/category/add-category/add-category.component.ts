import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../../../../services/category-service/category-service.service';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { ButtonsAdminComponent } from '../../../buttons/buttons-admin/buttons-admin.component';

@Component({
  selector: 'app-add-category',
  standalone: true,
  imports:
  [
    ButtonsAdminComponent,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.sass'
})
export class AddCategoryComponent implements OnInit {

  categoryName = new FormControl('', [Validators.required]);


  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
  }


  insertCategory() {
    const category = {
      name: this.categoryName.value
    };

    this.categoryService.insertCategory(category).subscribe()
  }
}
