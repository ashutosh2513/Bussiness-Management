import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_BASE_URL } from "../constants/api.constants";
import { getAuthHeaders } from "./auth-header.util";

@Injectable({ providedIn: "root" })
export class ApiService {
  private readonly base = API_BASE_URL;

  constructor(private http: HttpClient) {}

  searchCustomers(q: string) {
    return this.http.get<any[]>(`${this.base}/customers/search?q=${q}`, {
      headers: getAuthHeaders(),
    });
  }
  createInvoice(payload: any) {
    return this.http.post(`${this.base}/invoices`, payload, {
      headers: getAuthHeaders(),
    });
  }
  pay(payload: any) {
    return this.http.post(`${this.base}/payments`, payload, {
      headers: getAuthHeaders(),
    });
  }
  dashboard() {
    return this.http.get<any>(`${this.base}/dashboard`, {
      headers: getAuthHeaders(),
    });
  }
  register(payload: any) {
    return this.http.post(`${this.base}/auth/register`, payload);
  }
  login(payload: any) {
    return this.http.post(`${this.base}/auth/login`, payload);
  }
  saveCustomer(payload: any) {
    return this.http.post(`${this.base}/customers/add`, payload, {
      headers: getAuthHeaders(),
    });
  }
}
