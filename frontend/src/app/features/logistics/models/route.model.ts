export interface RoutePoint {
  latitude: number;
  longitude: number;
  isDepot: boolean;
  demand: number;
}

export interface VehicleRoute {
  vehicleId: string;
  points: RoutePoint[];
  color: string;
}

export interface RouteStat {
  label: string;
  value: string;
}
