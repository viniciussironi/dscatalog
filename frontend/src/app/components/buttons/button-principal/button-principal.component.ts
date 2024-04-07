import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-button-principal',
  standalone: true,
  imports: [],
  templateUrl: './button-principal.component.html',
  styleUrl: './button-principal.component.css'
})

export class ButtonPrincipalComponent {
  @Input("btn-text") btnText: string = "";
}