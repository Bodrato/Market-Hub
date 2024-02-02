import { Routes } from '@angular/router';
import { MainComponent } from './main/main.component';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ItemViewComponent } from './item-view/item-view.component';

export const routes: Routes = [
    { path: 'home', component: MainComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'item-view/:id', component: ItemViewComponent },
    { path: '', component: MainComponent }
]