import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgFor } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule, RouterLink, NgFor],
  template: `
  <div class="max-w-lg mx-auto bg-white p-6 rounded-xl shadow">
    <h2 class="text-2xl font-semibold">Create your account</h2>
    <input [(ngModel)]="fullName" placeholder="Full name" class="border p-2 w-full mt-4 rounded" />
    <input [(ngModel)]="email" placeholder="Email" class="border p-2 w-full mt-3 rounded" />
    <input [(ngModel)]="password" type="password" placeholder="Password" class="border p-2 w-full mt-3 rounded" />
    <select [(ngModel)]="role" class="border p-2 w-full mt-3 rounded">
      <option *ngFor="let r of roles" [value]="r">{{r}}</option>
    </select>
    <button (click)="submit()" class="w-full mt-4 bg-indigo-600 text-white p-2 rounded">Register</button>
    <p class="text-red-600 text-sm mt-2" *ngIf="error">{{error}}</p>
    <a routerLink="/login" class="text-indigo-600 text-sm mt-4 block">Already registered? Login</a>
  </div>
  `
})
export class RegisterComponent {
  private auth = inject(AuthService);
  private router = inject(Router);

  fullName = '';
  email = '';
  password = '';
  role = 'RETAILER';
  error = '';
  roles = ['MANUFACTURER', 'DISTRIBUTOR', 'RETAILER', 'ACCOUNTANT', 'FIELD_AGENT', 'END_CONSUMER'];

  submit() {
    this.error = '';
    this.auth.register({ fullName: this.fullName, email: this.email, password: this.password, role: this.role as any }).subscribe({
      next: (res: any) => {
        this.auth.setSession(res);
        this.router.navigateByUrl('/dashboard');
      },
      error: () => this.error = 'Unable to register (email may already exist)'
    });
  }
}
