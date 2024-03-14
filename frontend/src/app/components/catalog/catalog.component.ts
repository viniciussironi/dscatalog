import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../services/products.service';
import { ProductInterface } from '../../interfaces/products';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})

export class CatalogComponent implements OnInit {
  products!: ProductInterface ;
  
  constructor(private productService: ProductsService) {}

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe( // Refazer
      (data) => {
        this.products = data;
      },
      (error) => {
        console.error('Erro ao buscar produtos:', error);
      }
    );
  }
}
