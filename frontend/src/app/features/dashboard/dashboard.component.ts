import { Component, inject } from "@angular/core";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { ApiService } from "../../core/services/api.service";
import { ShellComponent } from "../../core/layout/shell.component";

interface DashboardData {
  todayRevenue?: number;
  outstandingDues?: number;
  lowStockItems?: number;
  invoicesToday?: number;
  topItems?: string[];
}

interface DashboardData {
  todayRevenue?: number;
  outstandingDues?: number;
  lowStockItems?: number;
  invoicesToday?: number;
  topItems?: string[];
}

@Component({
  standalone: true,
  selector: "app-dashboard",
  imports: [AsyncPipe, NgFor, NgIf, ShellComponent],
  templateUrl: "./dashboard.component.html",
  styleUrl: "./dashboard.component.css",
})
export class DashboardComponent {
  data$ = inject(ApiService).dashboard();

  readonly weekDays = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  readonly revenueTicks = [
    "?18K",
    "?12K",
    "?16K",
    "?23K",
    "?24K",
    "?25K",
    "?35K",
  ];
  readonly linePoints = [18, 20, 24, 23, 28, 31, 40];

  readonly activity = [
    {
      color: "#40c7a4",
      text: "Invoice INV-0842 generated for Rajesh Kumar",
      time: "2 mins ago",
    },
    {
      color: "#45a9dc",
      text: "Stock-in: 50 units of Parle-G Biscuit",
      time: "18 mins ago",
    },
    {
      color: "#f0b24f",
      text: "Payment received ?2,000 from Priya Mehta (UPI)",
      time: "45 mins ago",
    },
    {
      color: "#ef7575",
      text: "Coca Cola 500ml reached minimum stock",
      time: "1 hr ago",
    },
    {
      color: "#8c75d7",
      text: "New customer Walk-in Customer #42 billed",
      time: "2 hrs ago",
    },
  ];

  formatMoney(value: number | undefined): string {
    return new Intl.NumberFormat("en-IN").format(value ?? 0);
  }

  asData(value: unknown): DashboardData {
    return (value ?? {}) as DashboardData;
  }
}
