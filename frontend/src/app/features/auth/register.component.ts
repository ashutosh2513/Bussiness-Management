import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule, RouterLink, NgFor, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
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
