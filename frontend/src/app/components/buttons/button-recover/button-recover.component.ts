import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button-recover',
  standalone: true,
  imports: [],
  templateUrl: './button-recover.component.html',
  styleUrl: './button-recover.component.css'
})
export class ButtonRecoverComponent {
  @Input("btn-text") btnText: string = "";
}
