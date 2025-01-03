export interface OptimizationRequest {
  deliveryPoints: DeliveryPoint[];
  vehicles: Vehicle[];
  depot: Location;
  constraints: OptimizationConstraints;
}

export interface OptimizationResponse {
  routes: Route[];
  metrics: OptimizationMetrics;
  optimizationId: string;
}

export interface DeliveryPoint {
  id: string;
  latitude: number;
  longitude: number;
  demand: number;
  timeWindow: TimeWindow;
}

export interface Vehicle {
  id: string;
  capacity: number;
  maxDistance: number;
  availability: TimeWindow;
}

export interface TimeWindow {
  startTime: number;
  endTime: number;
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface Route {
  id: string;
  vehicleId: string;
  stops: RouteStop[];
  metrics: RouteMetrics;
}

export interface RouteStop {
  locationId: string;
  latitude: number;
  longitude: number;
  sequenceNumber: number;
  estimatedArrival: number;
  plannedDemand: number;
}

export interface RouteMetrics {
  totalDistance: number;
  totalDemand: number;
  totalTime: number;
  vehicleUtilization: number;
}

export interface OptimizationMetrics {
  totalDistance: number;
  totalDeliveries: number;
  vehiclesUsed: number;
  averageUtilization: number;
  totalCost: number;
  totalTime: number;
}

export interface OptimizationConstraints {
  maxRouteTime: number;
  allowPartialDeliveries: boolean;
  maxDistance: number;
  priorityCustomers: string[];
  maxStopsPerRoute: number;
  globalTimeWindow: TimeWindow;
  maxWaitingTime: number;
  respectVehicleRegions: boolean;
}
