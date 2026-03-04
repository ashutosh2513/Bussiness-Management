import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { getAuthHeaders } from "./auth-header.util";

@Injectable({ providedIn: "root" })
export class DashboardApiService {
  private readonly base = "https://business-management-hyoh.onrender.com/api";

  constructor(private http: HttpClient) {}

  fetchDashboardData() {
    return this.http.get(`${this.base}/dashboard`, {
      headers: getAuthHeaders(),
    });
  }
}
