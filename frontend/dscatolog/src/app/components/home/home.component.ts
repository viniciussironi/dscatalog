import { Component } from '@angular/core';
import { ButtonPrincipalComponent } from '../buttons/button-principal/button-principal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    ButtonPrincipalComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.sass'
})
export class HomeComponent {

}
