import { Routes } from '@angular/router';
import { BillingComponent } from './features/billing/billing.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { ProductsComponent } from './features/products/products.component';
import { LandingComponent } from './features/landing/landing.component';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'billing', component: BillingComponent },
  { path: 'products', component: ProductsComponent }
];
