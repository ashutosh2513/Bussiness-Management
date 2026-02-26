import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}

  searchCustomers(q: string) { return this.http.get<any[]>(`${this.base}/customers/search?q=${q}`); }
  createInvoice(payload: any) { return this.http.post(`${this.base}/invoices`, payload); }
  pay(payload: any) { return this.http.post(`${this.base}/payments`, payload); }
  dashboard() { return this.http.get<any>(`${this.base}/dashboard`); }
  register(payload: any) { return this.http.post(`${this.base}/auth/register`, payload); }
  login(payload: any) { return this.http.post(`${this.base}/auth/login`, payload); }
}
