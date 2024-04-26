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
import { canActivateAdmin } from './services/login/auth';
import { InputEmailComponent } from './components/login/recover/input-email/input-email.component';
import { MessageComponent } from './components/login/recover/message/message.component';
import { FormNewPasswordComponent } from './components/login/recover/form-new-password/form-new-password.component';
import { RegisterComponent } from './components/login/register/register.component';
import { RecoverComponent } from './components/login/recover/recover.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'products', component: CatalogComponent},
    {path: 'products/:id', component: ProductComponent},
    {path: 'admin',
     component: AdminComponent,
     canActivate: [ canActivateAdmin ],
     children: [
        {path: 'product', component: ProductCrudComponent},
        {path: 'category', component: CategoryCrudComponent},
        {path: 'user', component: UserCrudComponent},
        ]
    },
    {path: 'login', component: LoginComponent},
    {path: 'recover', component: RecoverComponent,
     children: [
        {path: 'identy', component: InputEmailComponent},
        {path: 'message', component: MessageComponent},
        {path: 'new-password', component: FormNewPasswordComponent},
        ]
    },
    {path: 'cadastrar', component: RegisterComponent},
    {path: '**', component: PageNotFoundComponentComponent},
];
