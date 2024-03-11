import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../../services/product.service';
import { Product } from '../../../Product';
import { environment } from '../../../../environments/environments';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  allProducts: Product[] = []
  baseApiUrl = environment.baseApiUrl

  constructor(private productService: ProductsService) {

  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((items) => {
      const data = items
      data.map((item) => {
        item.date = new Date(item.date).toLocaleDateString('pt-BR');
      })
      this.allProducts = data;
    })
  }
}
