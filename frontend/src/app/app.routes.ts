import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { AdminComponent } from './components/admin/admin.component';
import { LoginComponent } from './components/login/login.component';
import { ProductComponent } from './components/product/product.component';
import { PageNotFoundComponentComponent } from './components/page-not-found/page-not-found.component';
import { ProductCrudComponent } from './components/admin/product-crud/product-crud.component';
import { CategoryCrudComponent } from './components/admin/category-crud/category-crud.component';
import { UserCrudComponent } from './components/admin/user-crud/user-crud.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'product', component: CatalogComponent},
    {path: 'product/:id', component: ProductComponent},
    {path: 'admin', 
     component: AdminComponent,
     children: [
        {path: 'product', component: ProductCrudComponent},
        {path: 'category', component: CategoryCrudComponent},
        {path: 'user', component: UserCrudComponent},
     ]
    },
    {path: 'login', component: LoginComponent},
    {path: '**', component: PageNotFoundComponentComponent},
];
