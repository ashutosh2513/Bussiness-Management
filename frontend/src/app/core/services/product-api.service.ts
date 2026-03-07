import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  ProductCategoryOption,
  ProductRequest,
  UnitOption,
} from "../models/product.model";
import { getAuthHeaders } from "./auth-header.util";
import { API_BASE_URL } from "../constants/api.constants";

@Injectable({ providedIn: "root" })
export class ProductApiService {
  private readonly base = API_BASE_URL;

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
    return this.http.post(`${this.base}/products`, payload, {
      headers: getAuthHeaders(),
    });
  }
}
