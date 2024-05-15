import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule, DecimalPipe } from '@angular/common';

import { Page } from '../../interface/pageable-interface';
import { ProductInterface } from '../../interface/product-interface';
import { CategoryInterface } from '../../interface/category';
import { ProductService } from '../../services/product-service/product-service.service';
import { CategoryService } from '../../services/category-service/category-service.service';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterModule,
    DecimalPipe,
    CommonModule
  ],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.sass'
})
export class CatalogComponent implements OnInit {
  products: Page<ProductInterface> = { content: [], totalPages: 0, number: 0 };
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };

  productName = new FormControl('');
  categoryId = new FormControl('');
  selectedPage!: number;

  constructor(private productService: ProductService, private categoryService: CategoryService, private cdr: ChangeDetectorRef) { }

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
      this.productService.getProducts(String(pageNumber), productName, categoryId, '8').subscribe(
      (products: Page<ProductInterface>) => {
      this.products = products;
    });
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
