import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DecimalPipe } from '@angular/common';

import { ProductService } from '../../services/product-service/product-service.service';
import { ProductInterface } from '../../interface/product-interface';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [DecimalPipe],
  templateUrl: './product.component.html',
  styleUrl: './product.component.sass'
})
export class ProductComponent implements OnInit {

  product!: ProductInterface
  id: string = '';

  constructor(private route: ActivatedRoute, private productService: ProductService) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.getProductById();
  }


  getProductById() {
    this.productService.getProductById(Number(this.id)).subscribe(
    (product: ProductInterface) => {
    this.product = product;
    });
  }
}
