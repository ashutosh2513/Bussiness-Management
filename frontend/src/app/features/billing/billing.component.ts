import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { CartState } from '../../core/state/cart.state';
import { ApiService } from '../../core/services/api.service';

@Component({
  standalone: true,
  imports: [FormsModule, NgFor],
  templateUrl: './billing.component.html',
  styleUrl: './billing.component.css'
})
export class BillingComponent {
  cart = inject(CartState);
  api = inject(ApiService);

  customerQuery = '';
  customers = signal<any[]>([]);
  selectedCustomer = '';
  paymentMethod = 'CASH';
  paidAmount = 0;

  searchCustomer() { this.api.searchCustomers(this.customerQuery).subscribe(d => this.customers.set(d)); }

  checkout() {
    const payload = {
      customerId: this.selectedCustomer,
      paymentMethod: this.paymentMethod,
      paidAmount: this.paidAmount,
      items: this.cart.items().map(i => ({ productId: i.productId, quantity: i.qty }))
    };
    this.api.createInvoice(payload).subscribe(() => this.cart.clear());
  }
}
