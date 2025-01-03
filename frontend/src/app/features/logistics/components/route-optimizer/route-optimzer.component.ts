import {OptimizationRequest, OptimizationResponse} from '../../models/logistics.model';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {LogisticsService} from '../../services/logistics.service';
import {RouteStatsComponent} from '../route-stats/route-stats.component';
import {RouteControlsComponent} from '../route-controls/route-controls.component';
import {RouteMapComponent} from '../route-map/route-map.component';
import {CommonModule} from '@angular/common';
@Component({
  selector: 'app-route-optimizer',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouteMapComponent,
    RouteControlsComponent,
    RouteStatsComponent
  ],
  templateUrl: './route-optimizer.component.html'
})
export class RouteOptimizerComponent implements OnInit {
  optimizationForm: FormGroup;
  optimizationResult?: OptimizationResponse;
  activeRoutes: Set<string> = new Set();

  constructor(
    private fb: FormBuilder,
    private logisticsService: LogisticsService
  ) {
    this.optimizationForm = this.fb.group({
      depot: this.fb.group({
        latitude: [0],
        longitude: [0]
      }),
      vehicles: this.fb.array([]),
      deliveryPoints: this.fb.array([]),
      constraints: this.fb.group({
        maxRouteTime: [480],
        allowPartialDeliveries: [false],
        maxDistance: [1000],
        priorityCustomers: [[]],
        maxStopsPerRoute: [20],
        maxWaitingTime: [30],
        respectVehicleRegions: [false]
      })
    });
  }

  onRouteToggle(routeId: string) {
    if (this.activeRoutes.has(routeId)) {
      this.activeRoutes.delete(routeId);
    } else {
      this.activeRoutes.add(routeId);
    }
  }

  ngOnInit() {
    this.addVehicle();
    this.addDeliveryPoint();
  }

  get vehiclesFormArray() {
    return this.optimizationForm.get('vehicles') as FormArray;
  }

  get deliveryPointsFormArray() {
    return this.optimizationForm.get('deliveryPoints') as FormArray;
  }

  addVehicle() {
    const vehicleForm = this.fb.group({
      id: [''],
      capacity: [0],
      maxDistance: [0],
      availability: this.fb.group({
        startTime: [0],
        endTime: [0]
      })
    });
    this.vehiclesFormArray.push(vehicleForm);
  }

  addDeliveryPoint() {
    const pointForm = this.fb.group({
      id: [''],
      latitude: [0],
      longitude: [0],
      demand: [0],
      timeWindow: this.fb.group({
        startTime: [0],
        endTime: [0]
      })
    });
    this.deliveryPointsFormArray.push(pointForm);
  }

  onSubmit() {
    if (this.optimizationForm.valid) {
      const request: OptimizationRequest = this.optimizationForm.value;
      this.logisticsService.optimizeRoutes(request).subscribe({
        next: (response) => {
          this.optimizationResult = response;
        },
        error: (error) => {
          console.error('Optimization failed:', error);
        }
      });
    }
  }
}
