import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Route } from '../../models/logistics.model';

@Component({
  selector: 'app-route-controls',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './route-controls.component.html',
  styleUrls: ['./route-controls.component.scss']
})
export class RouteControlsComponent {
  @Input() routes: Route[] = [];
  @Output() routeToggled = new EventEmitter<string>();

  activeRoutes: Set<string> = new Set();

  toggleRoute(routeId: string) {
    if (this.activeRoutes.has(routeId)) {
      this.activeRoutes.delete(routeId);
    } else {
      this.activeRoutes.add(routeId);
    }
    this.routeToggled.emit(routeId);
  }

  isRouteActive(routeId: string): boolean {
    return this.activeRoutes.has(routeId);
  }
}
