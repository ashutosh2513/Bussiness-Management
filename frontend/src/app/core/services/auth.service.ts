import { Injectable, signal } from '@angular/core';
import { ApiService } from './api.service';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  readonly session = signal<AuthResponse | null>(null);

  constructor(private api: ApiService) {
    const raw = localStorage.getItem('smartbilling.session');
    if (raw) this.session.set(JSON.parse(raw));
  }

  register(payload: RegisterRequest) {
    return this.api.register(payload);
  }

  login(payload: LoginRequest) {
    return this.api.login(payload);
  }

  setSession(data: AuthResponse) {
    this.session.set(data);
    localStorage.setItem('smartbilling.session', JSON.stringify(data));
  }

  logout() {
    this.session.set(null);
    localStorage.removeItem('smartbilling.session');
  }
}
