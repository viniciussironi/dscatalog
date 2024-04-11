import { Component } from '@angular/core';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { AbstractControl, FormControl, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { CategoryService } from '../../../services/category/category.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-crud',
  standalone: true,
  imports: [ButtonsCrudComponent, ReactiveFormsModule, CommonModule],
  templateUrl: './category-crud.component.html',
  styleUrl: './category-crud.component.css'
})
export class CategoryCrudComponent {

  categoryName = new FormControl('', [Validators.required, Validators.email]);

  constructor(private categoryService: CategoryService) { }

  insertCategory() {
    const product = {
      name: this.categoryName.value,
    };
    
    this.categoryService.insertCategory(product).subscribe();
  }
}
