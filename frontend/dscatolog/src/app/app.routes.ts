import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { ProductComponent } from './components/product/product.component';
import { AdminComponent } from './components/admin/admin.component';
import { CategoryComponent } from './components/admin/category/category.component';
import { UserComponent } from './components/admin/user/user.component';
import { ProductUserComponent } from './components/admin/product/product.component';
import { canActivateAdmin } from './services/login-service/auth';
import { LoginComponent } from './components/login/login.component';
import { AddProductComponent } from './components/admin/product/add-product/add-product.component';
import { AddUserComponent } from './components/admin/user/add-user/add-user.component';
import { AddCategoryComponent } from './components/admin/category/add-category/add-category.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'products', component: CatalogComponent},
  {path: 'products/:id', component: ProductComponent},
  {path: 'admin', component: AdminComponent,
  canActivate: [ canActivateAdmin ],
  children:
  [
    {path: 'product', component: ProductUserComponent},
    {path: 'category', component: CategoryComponent},
    {path: 'user', component: UserComponent},
    {path: 'add-product', component: AddProductComponent},
    {path: 'edit-product/:id', component: AddProductComponent},
    {path: 'add-user', component: AddUserComponent},
    {path: 'edit-user/:id', component: AddUserComponent},
    {path: 'add-category', component: AddCategoryComponent},
    {path: 'edit-category/:id', component: AddCategoryComponent},
  ]
 },
 {path: 'login', component: LoginComponent},
];
