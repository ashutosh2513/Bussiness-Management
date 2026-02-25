import { Routes } from '@angular/router';
import { BillingComponent } from './features/billing/billing.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { ProductsComponent } from './features/products/products.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'billing', component: BillingComponent },
  { path: 'products', component: ProductsComponent }
];
