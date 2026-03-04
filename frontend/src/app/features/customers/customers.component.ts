import { Component } from "@angular/core";
import { NgFor } from "@angular/common";
import { RouterLink } from "@angular/router";

interface CustomerRow {
  id: number;
  name: string;
  phone: string;
  email: string;
  location: string;
  totalOrders: number;
  totalOrderValue: string;
  totalDue: string;
  status: "Active" | "Blacklisted";
}

@Component({
  selector: "app-customers",
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: "./customers.component.html",
  styleUrl: "./customers.component.css",
})
export class CustomersComponent {
  readonly customers: CustomerRow[] = [
    {
      id: 1,
      name: "Rajesh Kumar",
      phone: "9876543210",
      email: "rajesh@gmail.com",
      location: "Mumbai",
      totalOrders: 15,
      totalOrderValue: "INR 34,620",
      totalDue: "INR 950",
      status: "Active",
    },
    {
      id: 2,
      name: "Sneha Patel",
      phone: "8765432109",
      email: "sncha.patch@gmail.com",
      location: "Delhi",
      totalOrders: 12,
      totalOrderValue: "INR 29,850",
      totalDue: "INR 12,690",
      status: "Active",
    },
    {
      id: 3,
      name: "Amit Sharma",
      phone: "9988775655",
      email: "amit.chrm@gmail.com",
      location: "Bangalore",
      totalOrders: 17,
      totalOrderValue: "INR 45,190",
      totalDue: "INR 2,100",
      status: "Active",
    },
    {
      id: 4,
      name: "Pooja Mehta",
      phone: "9876123456",
      email: "poojarngmail.com",
      location: "Pune",
      totalOrders: 5,
      totalOrderValue: "INR 12,300",
      totalDue: "INR 0",
      status: "Active",
    },
    {
      id: 5,
      name: "Rohit Verma",
      phone: "9012245678",
      email: "rohitamnekl@gmail.com",
      location: "Jaipur",
      totalOrders: 8,
      totalOrderValue: "INR 19,450",
      totalDue: "INR 250",
      status: "Active",
    },
    {
      id: 6,
      name: "Arati Singh",
      phone: "7856341289",
      email: "aratoingh@gmail.com",
      location: "Lucknow",
      totalOrders: 6,
      totalOrderValue: "INR 15,120",
      totalDue: "INR 600",
      status: "Active",
    },
    {
      id: 7,
      name: "Sachin Soni",
      phone: "9988001122",
      email: "sachin.soni@yahoo.com",
      location: "Chennai",
      totalOrders: 9,
      totalOrderValue: "INR 22,950",
      totalDue: "INR 760",
      status: "Active",
    },
  ];
}
