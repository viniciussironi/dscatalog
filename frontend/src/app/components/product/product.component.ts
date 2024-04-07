import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product/product.service';
import { ProductInterface } from '../../interfaces/products';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [DecimalPipe],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{
  
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
