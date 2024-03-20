import { Component, NgModule, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { ProductInterface } from '../../interfaces/products';
import { CommonModule } from '@angular/common';
import { Page } from '../../interfaces/page';
import { CategoryService } from '../../services/category/category.service';
import { CategoryInterface } from '../../interfaces/category';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})

export class CatalogComponent implements OnInit {
  page: Page<ProductInterface> = { content: [], totalPages: 0, number: 0 };
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };
  
  constructor(private productService: ProductService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.getProducts(0);
    this.getCategories();
  }

  getProducts(pageNumber: number) {
    this.productService.getProducts(pageNumber).subscribe((page: Page<ProductInterface>) => {
      this.page = page;
    });
  }

  getCategories() {
    this.categoryService.getCategories().subscribe((categories: Page<CategoryInterface>) => {
      this.categories = categories;
    });
  }
}
