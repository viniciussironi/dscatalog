import { Component, NgModule, OnInit } from '@angular/core';
import { ProductsService } from '../../services/products.service';
import { ProductInterface } from '../../interfaces/products';
import { CommonModule } from '@angular/common';
import { Page } from '../../interfaces/page';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})

export class CatalogComponent implements OnInit {
  page: Page<ProductInterface> = { content: [], totalPages: 0, number: 0 };
  
  constructor(private productService: ProductsService) { }

  ngOnInit() {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts().subscribe((page: Page<ProductInterface>) => {
      this.page = page;
    });
  }
}
