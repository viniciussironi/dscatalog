import { Component, OnInit } from '@angular/core';
import { FormControl, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { CategoryService } from '../../../services/category/category.service';
import {ErrorStateMatcher} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'app-category-crud',
  standalone: true,
  imports: [
    ButtonsCrudComponent,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    CommonModule
  ],

  templateUrl: './category-crud.component.html',
  styleUrl: './category-crud.component.css'
})
export class CategoryCrudComponent implements OnInit {

  categoryName = new FormControl('', [Validators.required]);

  constructor(private categoryService: CategoryService,
    private formBuilder: NonNullableFormBuilder) { }

  ngOnInit(): void {

  }

  insertCategory() {

   // this.categoryService.insertCategory().subscribe();
  }
}
