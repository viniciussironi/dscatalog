import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { ErrorStateMatcher } from '@angular/material/core';

import { ButtonsAdminComponent } from '../../../buttons/buttons-admin/buttons-admin.component';
import { CategoryInterface } from '../../../../interface/category';
import { Page } from '../../../../interface/pageable-interface';
import { CategoryService } from '../../../../services/category-service/category-service.service';
import { ProductService } from '../../../../services/product-service/product-service.service';
import { ActivatedRoute } from '@angular/router';
import { ProductInterface } from '../../../../interface/product-interface';

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
    ButtonsAdminComponent,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.sass'
})

export class AddProductComponent implements OnInit {
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };
  product!: ProductInterface;
  id: string = '';

  productName = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(60)]);
  productPrice = new FormControl('', [Validators.required]);
  categoryId = new FormControl('', [Validators.required]);
  productDescription = new FormControl('', [Validators.required]);


  constructor(private categoryService: CategoryService, private productService: ProductService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.getCategories();
    this.getProductById();
  }

  getProductById() {
    this.productService.getProductById(Number(this.id)).subscribe(
    (product: ProductInterface) => {
    this.product = product;
    });
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
function getProductById(): ProductInterface {
  throw new Error('Function not implemented.');
}

