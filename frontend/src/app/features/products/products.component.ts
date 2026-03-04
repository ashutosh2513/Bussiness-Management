import { CurrencyPipe, NgClass, NgFor, NgIf } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";

type StockState = "In Stock" | "Low Stock" | "Out of Stock";

interface ProductRow {
  id: number;
  name: string;
  skuCode: string;
  category: string;
  price: number;
  stock: number;
  active: boolean;
  createdAt: string;
}

@Component({
  standalone: true,
  imports: [NgFor, NgIf, NgClass, FormsModule, RouterLink, CurrencyPipe],
  templateUrl: "./products.component.html",
  styleUrl: "./products.component.css",
})
export class ProductsComponent {
  searchTerm = "";
  categoryFilter = "All";
  sortBy: "recent" | "priceAsc" | "priceDesc" = "recent";
  stockFilter: "all" | "in-stock" | "low-stock" | "out-of-stock" = "all";

  pageSize = 8;
  currentPage = 1;
  readonly pageSizeOptions = [8, 10, 20];

  readonly products: ProductRow[] = [
    {
      id: 1,
      name: "Coca Cola 500ml",
      skuCode: "9679243210",
      category: "Beverages",
      price: 35,
      stock: 45,
      active: true,
      createdAt: "2026-03-03T07:00:00Z",
    },
    {
      id: 2,
      name: "Surf Excel 1kg",
      skuCode: "9113456710",
      category: "Cleaning",
      price: 180,
      stock: 0,
      active: true,
      createdAt: "2026-03-02T10:00:00Z",
    },
    {
      id: 3,
      name: "Notebook A4",
      skuCode: "9386779655",
      category: "Stationery",
      price: 60,
      stock: 2,
      active: true,
      createdAt: "2026-03-01T12:30:00Z",
    },
    {
      id: 4,
      name: "Maggie Noodles",
      skuCode: "9679113925",
      category: "Food",
      price: 15,
      stock: 100,
      active: true,
      createdAt: "2026-02-28T08:10:00Z",
    },
    {
      id: 5,
      name: "Parle-G Biscuit",
      skuCode: "9012246678",
      category: "Food",
      price: 25,
      stock: 50,
      active: true,
      createdAt: "2026-02-27T09:10:00Z",
    },
    {
      id: 6,
      name: "Veg Biryani",
      skuCode: "9220841289",
      category: "Food",
      price: 120,
      stock: 20,
      active: true,
      createdAt: "2026-02-26T06:00:00Z",
    },
    {
      id: 7,
      name: "Classic Pen",
      skuCode: "7808001122",
      category: "Stationery",
      price: 15,
      stock: 80,
      active: true,
      createdAt: "2026-02-25T07:00:00Z",
    },
    {
      id: 8,
      name: "Bluetooth Speaker",
      skuCode: "9572419289",
      category: "Electronics",
      price: 1500,
      stock: 25,
      active: true,
      createdAt: "2026-02-24T07:00:00Z",
    },
    {
      id: 9,
      name: "Olive Oil 1L",
      skuCode: "9527411488",
      category: "Food",
      price: 420,
      stock: 4,
      active: true,
      createdAt: "2026-02-23T07:00:00Z",
    },
    {
      id: 10,
      name: "Hand Wash 250ml",
      skuCode: "9041118423",
      category: "Cleaning",
      price: 85,
      stock: 0,
      active: false,
      createdAt: "2026-02-22T07:00:00Z",
    },
  ];

  get categories(): string[] {
    return ["All", ...new Set(this.products.map((item) => item.category))];
  }

  get inStockCount(): number {
    return this.products.filter((item) => this.stockState(item) === "In Stock")
      .length;
  }

  get lowStockCount(): number {
    return this.products.filter((item) => this.stockState(item) === "Low Stock")
      .length;
  }

  get outOfStockCount(): number {
    return this.products.filter(
      (item) => this.stockState(item) === "Out of Stock"
    ).length;
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.filteredProducts.length / this.pageSize));
  }

  get totalEntries(): number {
    return this.filteredProducts.length;
  }

  get paginatedProducts(): ProductRow[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredProducts.slice(start, start + this.pageSize);
  }

  get pageStart(): number {
    if (!this.filteredProducts.length) {
      return 0;
    }
    return (this.currentPage - 1) * this.pageSize + 1;
  }

  get pageEnd(): number {
    return Math.min(this.currentPage * this.pageSize, this.filteredProducts.length);
  }

  resetFilters(): void {
    this.searchTerm = "";
    this.categoryFilter = "All";
    this.sortBy = "recent";
    this.stockFilter = "all";
    this.currentPage = 1;
  }

  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
  }

  onFiltersChanged(): void {
    this.currentPage = 1;
  }

  stockState(item: ProductRow): StockState {
    if (item.stock <= 0) {
      return "Out of Stock";
    }
    if (item.stock <= 5) {
      return "Low Stock";
    }
    return "In Stock";
  }

  statusClass(item: ProductRow): string {
    const state = this.stockState(item);
    if (state === "Out of Stock") {
      return "status-chip out";
    }
    if (state === "Low Stock") {
      return "status-chip low";
    }
    return "status-chip in";
  }

  private get filteredProducts(): ProductRow[] {
    const keyword = this.searchTerm.trim().toLowerCase();

    const filtered = this.products.filter((item) => {
      const bySearch =
        !keyword ||
        item.name.toLowerCase().includes(keyword) ||
        item.skuCode.toLowerCase().includes(keyword) ||
        item.category.toLowerCase().includes(keyword);

      const byCategory =
        this.categoryFilter === "All" || item.category === this.categoryFilter;

      const state = this.stockState(item);
      const byStock =
        this.stockFilter === "all" ||
        (this.stockFilter === "in-stock" && state === "In Stock") ||
        (this.stockFilter === "low-stock" && state === "Low Stock") ||
        (this.stockFilter === "out-of-stock" && state === "Out of Stock");

      return bySearch && byCategory && byStock;
    });

    return filtered.sort((a, b) => {
      if (this.sortBy === "priceAsc") {
        return a.price - b.price;
      }
      if (this.sortBy === "priceDesc") {
        return b.price - a.price;
      }
      return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    });
  }
}
