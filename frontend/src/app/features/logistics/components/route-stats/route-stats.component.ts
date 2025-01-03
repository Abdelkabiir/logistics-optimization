import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OptimizationMetrics } from '../../models/logistics.model';

@Component({
  selector: 'app-route-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './route-stats.component.html',
  styleUrls: ['./route-stats.component.scss']
})
export class RouteStatsComponent {
  @Input() metrics?: OptimizationMetrics;

  formatMetric(value: number, unit: string = ''): string {
    return `${value.toFixed(2)}${unit}`;
  }
}
