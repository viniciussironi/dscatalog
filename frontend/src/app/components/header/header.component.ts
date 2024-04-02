import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  activeLink = '';

  ngOnInit(): void {
    this.setActive('/')
  }

  

  setActive(link: string) {
      this.activeLink = link;
  }

  isActive(link: string) {
      return this.activeLink === link;
  }
}
