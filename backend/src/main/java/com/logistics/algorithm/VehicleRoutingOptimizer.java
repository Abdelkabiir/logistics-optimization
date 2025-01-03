package com.logistics.algorithm;

import java.util.*;

public class VehicleRoutingOptimizer {
    private final List<Location> deliveryPoints;
    private final List<Vehicle> vehicles;
    private final Location depot;

    public static class Location {
        private final double latitude;
        private final double longitude;
        private final int demand;

        public Location(double latitude, double longitude, int demand) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.demand = demand;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getDemand() {
            return demand;
        }

        public double distanceTo(Location other) {
            double lat1 = Math.toRadians(latitude);
            double lon1 = Math.toRadians(longitude);
            double lat2 = Math.toRadians(other.latitude);
            double lon2 = Math.toRadians(other.longitude);

            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;

            double a = Math.pow(Math.sin(dlat/2), 2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                            Math.pow(Math.sin(dlon/2), 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return 6371 * c; // Earth's radius in km
        }
    }

    public static class Vehicle {
        private final int capacity;
        private final double maxDistance;
        private List<Location> route;
        private double currentDistance;
        private int currentLoad;

        public Vehicle(int capacity, double maxDistance) {
            this.capacity = capacity;
            this.maxDistance = maxDistance;
            this.route = new ArrayList<>();
            this.currentDistance = 0;
            this.currentLoad = 0;
        }

        public int getCapacity() {
            return capacity;
        }

        public double getCurrentDistance() {
            return currentDistance;
        }

        public int getCurrentLoad() {
            return currentLoad;
        }

        public boolean canAddLocation(Location location, Location current) {
            double additionalDistance = current.distanceTo(location);
            return currentLoad + location.demand <= capacity &&
                    currentDistance + additionalDistance <= maxDistance;
        }
    }

    public VehicleRoutingOptimizer(List<Location> deliveryPoints, List<Vehicle> vehicles, Location depot) {
        this.deliveryPoints = new ArrayList<>(deliveryPoints);
        this.vehicles = vehicles;
        this.depot = depot;
    }

    public Map<Vehicle, List<Location>> optimizeRoutes() {
        Map<Vehicle, List<Location>> solution = new HashMap<>();
        List<Location> unassignedPoints = new ArrayList<>(deliveryPoints);

        // Initialize each vehicle with empty route
        for (Vehicle vehicle : vehicles) {
            solution.put(vehicle, new ArrayList<>());
            vehicle.route = solution.get(vehicle);
            vehicle.route.add(depot);
        }

        // Savings algorithm implementation
        while (!unassignedPoints.isEmpty()) {
            double maxSaving = -1;
            Location bestPoint = null;
            Vehicle bestVehicle = null;
            int bestPosition = -1;

            for (Location point : unassignedPoints) {
                for (Vehicle vehicle : vehicles) {
                    for (int i = 1; i <= vehicle.route.size(); i++) {
                        Location before = vehicle.route.get(i-1);
                        Location after = i < vehicle.route.size() ? vehicle.route.get(i) : depot;

                        double saving = before.distanceTo(after) -
                                (before.distanceTo(point) + point.distanceTo(after));

                        if (saving > maxSaving && vehicle.canAddLocation(point, before)) {
                            maxSaving = saving;
                            bestPoint = point;
                            bestVehicle = vehicle;
                            bestPosition = i;
                        }
                    }
                }
            }

            if (bestPoint != null) {
                bestVehicle.route.add(bestPosition, bestPoint);
                bestVehicle.currentLoad += bestPoint.demand;
                unassignedPoints.remove(bestPoint);

                // Update vehicle's current distance
                double newDistance = 0;
                Location previous = depot;
                for (Location loc : bestVehicle.route) {
                    newDistance += previous.distanceTo(loc);
                    previous = loc;
                }
                newDistance += previous.distanceTo(depot);
                bestVehicle.currentDistance = newDistance;
            } else {
                break; // No feasible insertion found
            }
        }

        return solution;
    }
}