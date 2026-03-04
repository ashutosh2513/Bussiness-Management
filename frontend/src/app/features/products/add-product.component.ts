import { Component, OnInit, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { RouterLink } from "@angular/router";
import { forkJoin } from "rxjs";
import {
  ProductCategoryOption,
  ProductRequest,
  UnitOption,
} from "../../core/models/product.model";
import { ProductApiService } from "../../core/services/product-api.service";

@Component({
  selector: "app-add-product",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule,
    RouterLink,
  ],
  templateUrl: "./add-product.component.html",
  styleUrl: "./add-product.component.css",
})
export class AddProductComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly productApi = inject(ProductApiService);
  private readonly snackBar = inject(MatSnackBar);

  readonly productForm = this.fb.group({
    name: ["", [Validators.required]],
    categoryId: [null as number | null, [Validators.required]],
    unitId: [null as number | null, [Validators.required]],
    purchasePrice: [null as number | null, [Validators.required, Validators.min(0)]],
    sellingPrice: [null as number | null, [Validators.required, Validators.min(0)]],
    gstPercent: [null as number | null, [Validators.required, Validators.min(0)]],
    skuCode: [""],
    retailerId: [1],
  });

  categories: ProductCategoryOption[] = [];
  units: UnitOption[] = [];
  loadingOptions = false;
  submitting = false;

  ngOnInit(): void {
    this.loadDropdowns();
  }

  saveProduct(): void {
    if (this.productForm.invalid || this.submitting) {
      this.productForm.markAllAsTouched();
      return;
    }

    const payload = this.productForm.getRawValue() as ProductRequest;
    if (payload.sellingPrice < payload.purchasePrice) {
      this.snackBar.open(
        "Selling price must be greater than or equal to purchase price",
        "Close",
        { duration: 3000 }
      );
      return;
    }

    this.submitting = true;
    this.productApi.createProduct(payload).subscribe({
      next: () => {
        this.snackBar.open("Product Added Successfully", "Close", {
          duration: 3000,
        });
        this.productForm.reset({
          name: "",
          categoryId: null,
          unitId: null,
          purchasePrice: null,
          sellingPrice: null,
          gstPercent: null,
          skuCode: "",
          retailerId: 1,
        });
        this.submitting = false;
      },
      error: () => {
        this.submitting = false;
      },
    });
  }

  private loadDropdowns(): void {
    this.loadingOptions = true;

    forkJoin({
      categories: this.productApi.getProductCategories(),
      units: this.productApi.getUnits(),
    }).subscribe({
      next: ({ categories, units }) => {
        this.categories = categories ?? [];
        this.units = units ?? [];
      },
      error: () => {
        this.categories = [];
        this.units = [];
      },
      complete: () => {
        this.loadingOptions = false;
      },
    });
  }
}
