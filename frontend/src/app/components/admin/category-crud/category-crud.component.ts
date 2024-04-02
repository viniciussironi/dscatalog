import { Component } from '@angular/core';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CategoryService } from '../../../services/category/category.service';

@Component({
  selector: 'app-category-crud',
  standalone: true,
  imports: [ButtonsCrudComponent, ReactiveFormsModule],
  templateUrl: './category-crud.component.html',
  styleUrl: './category-crud.component.css'
})
export class CategoryCrudComponent {

  categoryName = new FormControl();

  constructor(private categoryService: CategoryService) { }

  onSubmit(event: Event) {
    event.preventDefault();
    this.insertCategory();
  }


  insertCategory() {
    const product = {
      name: this.categoryName.value,
    };
    
    this.categoryService.insertCategory(product).subscribe()
  }
}
