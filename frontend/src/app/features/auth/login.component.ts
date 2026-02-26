import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule, RouterLink, NgIf],
  template: `
  <div class="max-w-md mx-auto bg-white p-6 rounded-xl shadow">
    <h2 class="text-2xl font-semibold">Login</h2>
    <p class="text-slate-600 text-sm mt-1">Access your SmartBilling workspace.</p>
    <input [(ngModel)]="email" placeholder="Email" class="border p-2 w-full mt-4 rounded" />
    <input [(ngModel)]="password" type="password" placeholder="Password" class="border p-2 w-full mt-3 rounded" />
    <button (click)="submit()" class="w-full mt-4 bg-indigo-600 text-white p-2 rounded">Login</button>
    <p class="text-red-600 text-sm mt-2" *ngIf="error">{{error}}</p>
    <a routerLink="/register" class="text-indigo-600 text-sm mt-4 block">Don't have an account? Register</a>
  </div>
  `
})
export class LoginComponent {
  private auth = inject(AuthService);
  private router = inject(Router);

  email = '';
  password = '';
  error = '';

  submit() {
    this.error = '';
    this.auth.login({ email: this.email, password: this.password }).subscribe({
      next: (res: any) => {
        this.auth.setSession(res);
        this.router.navigateByUrl('/dashboard');
      },
      error: () => this.error = 'Invalid credentials'
    });
  }
}
