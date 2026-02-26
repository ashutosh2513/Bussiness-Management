import { Injectable, signal, computed } from '@angular/core';

export interface CartItem { productId: string; name: string; qty: number; price: number; taxRate: number; }

@Injectable({ providedIn: 'root' })
export class CartState {
  readonly items = signal<CartItem[]>([]);
  readonly subtotal = computed(() => this.items().reduce((s, i) => s + i.qty * i.price, 0));
  readonly tax = computed(() => this.items().reduce((s, i) => s + i.qty * i.price * i.taxRate / 100, 0));
  readonly total = computed(() => this.subtotal() + this.tax());

  add(item: CartItem) {
    const existing = this.items().find(i => i.productId === item.productId);
    if (existing) {
      this.items.update(list => list.map(i => i.productId === item.productId ? { ...i, qty: i.qty + 1 } : i));
    } else {
      this.items.update(list => [...list, item]);
    }
  }

  updateQty(productId: string, qty: number) {
    this.items.update(list => list.map(i => i.productId === productId ? { ...i, qty } : i));
  }

  clear() { this.items.set([]); }
}
