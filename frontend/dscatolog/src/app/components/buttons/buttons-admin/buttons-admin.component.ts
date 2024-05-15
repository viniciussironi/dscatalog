import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-buttons-admin',
  standalone: true,
  imports: [],
  templateUrl: './buttons-admin.component.html',
  styleUrl: './buttons-admin.component.sass'
})
export class ButtonsAdminComponent {
  @Output("submit") onSubmit = new EventEmitter();
  @Output("back") goBack = new Router();


  submit() {
    this.onSubmit.emit();
  }

  back() {
    this.goBack.navigate(['/admin']);
  }
}

