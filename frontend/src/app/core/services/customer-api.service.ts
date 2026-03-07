import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { CustomerRequest } from "../models/customer.model";
import { getAuthHeaders } from "./auth-header.util";
import { API_BASE_URL } from "../constants/api.constants";

@Injectable({ providedIn: "root" })
export class CustomerApiService {
  private readonly base = API_BASE_URL;

  constructor(private http: HttpClient) {}

  saveCustomer(payload: CustomerRequest) {
    return this.http.post(`${this.base}/customers/add`, payload, {
      headers: getAuthHeaders(),
    });
  }
}
