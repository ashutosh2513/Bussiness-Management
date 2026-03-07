import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { getAuthHeaders } from "./auth-header.util";
import { API_BASE_URL } from "../constants/api.constants";

@Injectable({ providedIn: "root" })
export class DashboardApiService {
  private readonly base = API_BASE_URL;

  constructor(private http: HttpClient) {}

  fetchDashboardData() {
    return this.http.get(`${this.base}/dashboard`, {
      headers: getAuthHeaders(),
    });
  }
}
