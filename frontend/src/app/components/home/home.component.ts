import { NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import { ButtonPrincipalComponent } from '../buttons/button-principal/button-principal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgOptimizedImage, ButtonPrincipalComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
