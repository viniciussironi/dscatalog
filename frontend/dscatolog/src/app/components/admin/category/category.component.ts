import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CategoryService } from '../../../services/category-service/category-service.service';
import { Page } from '../../../interface/pageable-interface';
import { CategoryInterface } from '../../../interface/category';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.sass'
})
export class CategoryComponent implements OnInit {
  categories: Page<CategoryInterface> = { content: [], totalPages: 0, number: 0 };
  selectedPage!: number;

  constructor(private categoryService: CategoryService, private cdr: ChangeDetectorRef, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getCategories();
    this.selectPage(0);
  }

  getCategories() {
      this.categoryService.getCategories().subscribe(
      (categories: Page<CategoryInterface>) => {
      this.categories = categories;
    });
  }

  deleteCategory(id: number) {
    this.categoryService.deleteCategory(id).subscribe(() => {
      this.getCategories();
      this.snackBar.open('Categoria deletada com sucesso!', 'X', {duration: 5000, verticalPosition: 'top', horizontalPosition: 'center'})
    });
  }

  nextPage() {
    if (this.categories.number < this.categories.totalPages - 1) {
      this.categories.number++;
      this.selectedPage = this.categories.number;
      this.getCategories();
    }
  }

  backPage() {
    if (this.categories.number > 0) {
      this.categories.number --;
      this.selectedPage = this.categories.number;
      this.getCategories();
    }
  }

  selectPage(page: number) {
    this.selectedPage = page;
    this.cdr.markForCheck();
  }
}

