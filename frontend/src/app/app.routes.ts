import { Routes } from "@angular/router";
import { BillingComponent } from "./features/billing/billing.component";
import { DashboardComponent } from "./features/dashboard/dashboard.component";
import { ProductsComponent } from "./features/products/products.component";
import { LandingComponent } from "./features/landing/landing.component";
import { LoginComponent } from "./features/auth/login.component";
import { RegisterComponent } from "./features/auth/register.component";
import { AddCustomerComponent } from "./features/customers/add-customer.component";
import { AddProductComponent } from "./features/products/add-product.component";
import { CustomersComponent } from "./features/customers/customers.component";

export const routes: Routes = [
  { path: "", component: LandingComponent },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "dashboard", component: DashboardComponent },
  { path: "products", component: ProductsComponent },
  { path: "products/add", component: AddProductComponent },
  { path: "billing", component: BillingComponent },
  { path: "customers", component: CustomersComponent },
  { path: "customers/add", component: AddCustomerComponent },
];
