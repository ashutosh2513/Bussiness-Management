export type UserRole = 'MANUFACTURER' | 'DISTRIBUTOR' | 'RETAILER' | 'ACCOUNTANT' | 'FIELD_AGENT' | 'END_CONSUMER';

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  fullName: string;
  email: string;
  role: UserRole;
}
