import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  selector: 'app-buttons-crud',
  standalone: true,
  imports: [],
  templateUrl: './buttons-crud.component.html',
  styleUrl: './buttons-crud.component.css'
})
export class ButtonsCrudComponent {
  @Output("submit") onSubmit = new EventEmitter();
  @Output("back") goBack = new Router();


  submit() {
    this.onSubmit.emit();
  }

  back() {
    this.goBack.navigate(['/']);
  }
}
