import { Component, OnInit } from '@angular/core';
import { Page } from '../../../interfaces/page';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroupDirective, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ErrorStateMatcher } from '@angular/material/core';

import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryInterface } from '../../../interfaces/category';
import { ProductService } from '../../../services/product/product.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-product-crud',
  standalone: true,
  imports: [
    ButtonsCrudComponent,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './product-crud.component.html',
  styleUrl: './product-crud.component.css'
})

export class ProductCrudComponent implements OnInit {
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };

  productName = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(60)]);
  productPrice = new FormControl('', [Validators.required]);
  categoryId = new FormControl('', [Validators.required]);
  productDescription = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();

  constructor(private categoryService: CategoryService, private productService: ProductService) { }


  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(
      (categories: Page<CategoryInterface>) => {
        this.categories = categories;
      });
  }

  insertProduct() {
    const product = {
      name: this.productName.value,
      price: this.productPrice.value,
      description: this.productDescription.value,
      imgUrl: 'olá',
      categories: [{ id: this.categoryId.value }]
    };

    this.productService.insertProduct(product).subscribe()
  }
}
