import { Component, NgModule } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { DashboardComponent } from "./features/dashboard/dashboard.component";
import { ShellComponent } from "./core/layout/shell.component";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.css",
})
export class AppComponent {}
