import { Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { CustomerRequest } from "../../core/models/customer.model";
import { CustomerApiService } from "../../core/services/customer-api.service";
import { RouterLink } from "@angular/router";
import { CustomersComponent } from "./customers.component";

@Component({
  selector: "app-add-customer",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    RouterLink,
    CustomersComponent,
  ],
  templateUrl: "./add-customer.component.html",
  styleUrl: "./add-customer.component.css",
})
export class AddCustomerComponent {
  private readonly fb = inject(FormBuilder);
  private readonly customerApi = inject(CustomerApiService);
  private readonly snackBar = inject(MatSnackBar);

  readonly customerForm = this.fb.group({
    name: ["", [Validators.required]],
    phone: ["", [Validators.required, Validators.minLength(10)]],
    email: [""],
    gstNumber: [""],
    address: [""],
    // retailerId: [1],
  });

  submitting = false;

  saveCustomer() {
    if (this.customerForm.invalid || this.submitting) {
      this.customerForm.markAllAsTouched();
      return;
    }

    this.submitting = true;
    const raw = this.customerForm.getRawValue();
    const payload: CustomerRequest = {
      name: raw.name ?? "",
      phone: raw.phone ?? "",
      email: raw.email ?? "",
      gstNumber: raw.gstNumber ?? "",
      address: raw.address ?? "",
      // retailerId: Number(raw.retailerId) || 1,
    };

    this.customerApi.saveCustomer(payload).subscribe({
      next: () => {
        this.snackBar.open("Customer Added Successfully", "Close", {
          duration: 3000,
        });
        this.customerForm.reset({
          name: "",
          phone: "",
          email: "",
          gstNumber: "",
          address: "",
          //  retailerId: 1,
        });
        this.submitting = false;
      },
      error: () => {
        this.submitting = false;
      },
    });
  }
}
