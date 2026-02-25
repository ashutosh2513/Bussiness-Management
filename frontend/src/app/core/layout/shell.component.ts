import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterLink],
  template: `
  <div class="min-h-screen grid grid-cols-[240px_1fr] bg-slate-50">
    <aside class="bg-slate-900 text-white p-4">
      <h1 class="font-bold text-xl">SmartBilling</h1>
      <nav class="mt-4 space-y-2">
        <a routerLink="/" class="block">Dashboard</a>
        <a routerLink="/billing" class="block">Billing</a>
        <a routerLink="/products" class="block">Products</a>
      </nav>
    </aside>
    <main class="p-4"><ng-content /></main>
  </div>`
})
export class ShellComponent {}
