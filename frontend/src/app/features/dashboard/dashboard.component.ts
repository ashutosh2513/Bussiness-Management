import { Component, inject } from '@angular/core';
import { AsyncPipe, NgFor } from '@angular/common';
import { ApiService } from '../../core/services/api.service';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [AsyncPipe, NgFor],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent { data$ = inject(ApiService).dashboard(); }
