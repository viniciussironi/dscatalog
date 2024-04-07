import { Component } from '@angular/core';
import { ButtonRecoverComponent } from '../../../buttons/button-recover/button-recover.component';

@Component({
  selector: 'app-message',
  standalone: true,
  imports: [ButtonRecoverComponent],
  templateUrl: './message.component.html',
  styleUrl: './message.component.css'
})
export class MessageComponent {

}
