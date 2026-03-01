import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { CustomerRequest } from "../models/customer.model";

@Injectable({ providedIn: "root" })
export class CustomerApiService {
  private readonly base = "https://business-management-hyoh.onrender.com/api";

  constructor(private http: HttpClient) {}

  saveCustomer(payload: CustomerRequest) {
    return this.http.post(`${this.base}/customers`, payload);
  }
}
