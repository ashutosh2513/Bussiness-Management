import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  ProductCategoryOption,
  ProductRequest,
  UnitOption,
} from "../models/product.model";

@Injectable({ providedIn: "root" })
export class ProductApiService {
  private readonly base = "https://business-management-hyoh.onrender.com/api";

  constructor(private http: HttpClient) {}

  getProductCategories() {
    return this.http.get<ProductCategoryOption[]>(
      `${this.base}/product-categories`
    );
  }

  getUnits() {
    return this.http.get<UnitOption[]>(`${this.base}/units`);
  }

  createProduct(payload: ProductRequest) {
    return this.http.post(`${this.base}/products`, payload);
  }
}
