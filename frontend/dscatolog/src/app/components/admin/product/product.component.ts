import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProductInterface } from '../../../interface/product-interface';
import { Page } from '../../../interface/pageable-interface';
import { ProductService } from '../../../services/product-service/product-service.service';
import { CategoryService } from '../../../services/category-service/category-service.service';
import { CategoryInterface } from '../../../interface/category';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterModule,
    DecimalPipe,
    CommonModule,
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.sass'
})
export class ProductUserComponent implements OnInit {
  products: Page<ProductInterface> = { content: [], totalPages: 0, number: 0 };
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };

  productName = new FormControl('');
  categoryId = new FormControl('');
  selectedPage!: number;

  constructor(private productService: ProductService, private categoryService: CategoryService, private cdr: ChangeDetectorRef, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getProducts(0, '', '');
    this.getCategories();
    this.selectPage(0);
  }

  onSubmit(event: Event) {
    event.preventDefault();
    this.getProducts(0, this.productName.value + '', String(this.categoryId.value));
  }

  resetFilters() {
    this.productName.setValue('');
    this.categoryId.setValue('');
    this.getProducts(0, '', '');
  }

  getProducts(pageNumber: number, productName: string, categoryId: string) {
      this.productService.getProducts(String(pageNumber), productName, categoryId, '4').subscribe(
      (products: Page<ProductInterface>) => {
      this.products = products;
    });
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe(
      () => {
      this.getProducts(this.products.number, '', '');
      this.snackBar.open('Produto deletado com sucesso!', 'X', {duration: 5000, verticalPosition: 'top', horizontalPosition: 'center'})
      }
    );
  }

  getCategories() {
      this.categoryService.getCategories().subscribe(
      (categories: Page<CategoryInterface>) => {
      this.categories = categories;
    });
  }

  nextPage() {
    if (this.products.number < this.products.totalPages - 1) {
      this.products.number++;
      this.selectedPage = this.products.number;
      this.getProducts(this.products.number, this.productName.value || '', String(this.categoryId.value));
    }
  }

  backPage() {
    if (this.products.number > 0) {
      this.products.number --;
      this.selectedPage = this.products.number;
      this.getProducts(this.products.number, this.productName.value || '', String(this.categoryId.value));
    }
  }

  selectPage(page: number) {
    this.selectedPage = page;
    this.cdr.markForCheck();
  }
}

