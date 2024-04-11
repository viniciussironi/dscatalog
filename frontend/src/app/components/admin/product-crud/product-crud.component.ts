import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryInterface } from '../../../interfaces/category';
import { Page } from '../../../interfaces/page';
import { CommonModule } from '@angular/common';
import { ButtonsCrudComponent } from '../../buttons/buttons-crud/buttons-crud.component';
import { ProductService } from '../../../services/product/product.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ProductInsertInterface } from '../../../interfaces/product-insert';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-product-crud',
  standalone: true,
  imports: [ButtonsCrudComponent, CommonModule, ReactiveFormsModule],
  templateUrl: './product-crud.component.html',
  styleUrl: './product-crud.component.css'
})

export class ProductCrudComponent implements OnInit {
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };
  
  productName = new FormControl;
  productPrice = new FormControl;
  categoryId = new FormControl('');
  productDescription = new FormControl;

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
