import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Page } from '../../interfaces/page';
import { ProductService } from '../../services/product/product.service';
import { ProductInterface } from '../../interfaces/products';
import { CategoryService } from '../../services/category/category.service';
import { CategoryInterface } from '../../interfaces/category';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, DecimalPipe, CommonModule],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})

export class CatalogComponent implements OnInit {
  products: Page<ProductInterface> = { content: [], totalPages: 0, number: 0 };
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };
  productName = new FormControl('');
  categoryId = new FormControl('');
  selectedPage!: number;
  
  constructor(private productService: ProductService, private categoryService: CategoryService, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.getProducts(0, "", "");
    this.getCategories();
    this.selectPage(0);
  }

  onSubmit(event: Event) {
    event.preventDefault();
    this.getProducts(0, this.productName.value + '', this.categoryId.value + '');
  }

  resetFilters() {
    this.productName.setValue('');
    this.categoryId.setValue('');
    this.getProducts(0, "", "");
  }

  getProducts(pageNumber: number, productName: string, categoryId: string) {
      this.productService.getProducts(pageNumber + "", productName, categoryId + "").subscribe(
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

  avancarPagina() {
    if (this.products.number < this.products.totalPages - 1) {
      this.products.number++;
      this.selectedPage = this.products.number;
      this.getProducts(this.products.number, this.productName.value + '', this.categoryId.value + '');
    }
  }

  voltarPagina() {
    if (this.products.number > 0) {
      this.products.number --;
      this.selectedPage = this.products.number;
      this.getProducts(this.products.number, this.productName.value + '', this.categoryId.value + '');
    }
  }

  selectPage(page: number) {
    this.selectedPage = page;
    this.cdr.markForCheck();
  }
}
