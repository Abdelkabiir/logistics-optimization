import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { CommonModule } from '@angular/common';
import * as L from 'leaflet';
import { Route } from '../../models/logistics.model';

@Component({
  selector: 'app-route-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './route-map.component.html',
  styleUrls: ['./route-map.component.scss']
})
export class RouteMapComponent implements OnInit, OnChanges {
  @Input() routes: Route[] = [];
  @Input() activeRoutes: Set<string> = new Set();
  @Output() routeToggled = new EventEmitter<string>();

  private map!: L.Map;
  private routeLayers: Map<string, L.LayerGroup> = new Map();

  ngOnInit() {
    this.initializeMap();
    this.loadRoutes();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['activeRoutes']) {
      this.updateRouteVisibility();
    }
  }

  private updateRouteVisibility() {
    this.routeLayers.forEach((layer, routeId) => {
      if (this.activeRoutes.has(routeId)) {
        layer.addTo(this.map);
      } else {
        this.map.removeLayer(layer);
      }
    });
  }

  private initializeMap() {
    this.map = L.map('map').setView([0, 0], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);
  }

  private loadRoutes() {
    if (!this.routes.length) return;

    const bounds = new L.LatLngBounds([]);
    this.routes.forEach(route => {
      const layerGroup = this.createRouteLayer(route);
      if (layerGroup) {
        this.routeLayers.set(route.id, layerGroup);
        layerGroup.addTo(this.map);
        route.stops.forEach(stop => {
          bounds.extend([stop.latitude, stop.longitude]);
        });
      }
    });

    this.map.fitBounds(bounds, { padding: [50, 50] });
  }

  private createRouteLayer(route: Route): L.LayerGroup {
    const layerGroup = L.layerGroup();
    const points = route.stops.map(stop => [stop.latitude, stop.longitude] as L.LatLngTuple);

    // Draw route line
    L.polyline(points, {
      color: this.getRouteColor(route.id),
      weight: 3,
      opacity: 0.8
    }).addTo(layerGroup);

    // Add markers for stops
    route.stops.forEach((stop, index) => {
      L.marker([stop.latitude, stop.longitude])
        .bindPopup(this.createPopupContent(stop, index))
        .addTo(layerGroup);
    });

    return layerGroup;
  }

  private getRouteColor(routeId: string): string {
    const colors = ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF'];
    // Simple hash function for string
    const hash = routeId.split('').reduce((acc, char) => {
      return char.charCodeAt(0) + ((acc << 5) - acc);
    }, 0);
    return colors[Math.abs(hash % colors.length)];
  }

  private createPopupContent(stop: any, index: number): string {
    return `
      <div>
        <h3>Stop #${index + 1}</h3>
        <p>Location ID: ${stop.locationId}</p>
        <p>Demand: ${stop.plannedDemand}</p>
      </div>
    `;
  }
}
