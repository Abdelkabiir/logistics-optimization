package com.logistics.service;

import com.logistics.algorithm.VehicleRoutingOptimizer;
import com.logistics.exception.ResourceNotFoundException;
import com.logistics.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RoutingService {
    private final Map<String, Route> routeStorage = new ConcurrentHashMap<>();
    private final WeatherService weatherService;
    private final TrafficService trafficService;

    public RoutingService(WeatherService weatherService, TrafficService trafficService) {
        this.weatherService = weatherService;
        this.trafficService = trafficService;
    }

    public OptimizationResponse optimizeRoutes(OptimizationRequest request) {
        VehicleRoutingOptimizer optimizer = new VehicleRoutingOptimizer(
                convertToOptimizerLocations(request.getDeliveryPoints()),
                convertToOptimizerVehicles(request.getVehicles()),
                convertToOptimizerLocation(request.getDepot())
        );

        Map<VehicleRoutingOptimizer.Vehicle, List<VehicleRoutingOptimizer.Location>> solution =
                optimizer.optimizeRoutes();

        List<Route> routes = convertToRoutes(solution);
        String optimizationId = UUID.randomUUID().toString();

        // Store in memory
        routes.forEach(route -> {
            route.setOptimizationId(optimizationId);
            routeStorage.put(route.getId(), route);
        });

        return new OptimizationResponse(routes, calculateMetrics(routes), optimizationId);
    }

    public Route getRouteDetails(String routeId) {
        Route route = routeStorage.get(routeId);
        if (route == null) {
            throw new ResourceNotFoundException("Route not found: " + routeId);
        }
        return route;
    }

    private OptimizationMetrics calculateMetrics(List<Route> routes) {
        // Implementation of metrics calculation
        return new OptimizationMetrics();
    }

    // Add these methods to RoutingService.java
    private List<VehicleRoutingOptimizer.Location> convertToOptimizerLocations(List<DeliveryPoint> deliveryPoints) {
        return deliveryPoints.stream()
                .map(dp -> new VehicleRoutingOptimizer.Location(
                        dp.getLatitude(),
                        dp.getLongitude(),
                        dp.getDemand()
                ))
                .collect(Collectors.toList());
    }

    private List<VehicleRoutingOptimizer.Vehicle> convertToOptimizerVehicles(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(v -> new VehicleRoutingOptimizer.Vehicle(
                        v.getCapacity(),
                        v.getMaxDistance()
                ))
                .collect(Collectors.toList());
    }

    private VehicleRoutingOptimizer.Location convertToOptimizerLocation(Location location) {
        return new VehicleRoutingOptimizer.Location(
                location.getLatitude(),
                location.getLongitude(),
                0 // Depot has no demand
        );
    }

    private List<Route> convertToRoutes(Map<VehicleRoutingOptimizer.Vehicle, List<VehicleRoutingOptimizer.Location>> solution) {
        List<Route> routes = new ArrayList<>();
        int vehicleIndex = 0;

        for (Map.Entry<VehicleRoutingOptimizer.Vehicle, List<VehicleRoutingOptimizer.Location>> entry : solution.entrySet()) {
            Route route = new Route();
            route.setId(UUID.randomUUID().toString());
            route.setVehicleId("V" + vehicleIndex++);

            List<RouteStop> stops = new ArrayList<>();
            int stopIndex = 0;

            for (VehicleRoutingOptimizer.Location location : entry.getValue()) {
                RouteStop stop = new RouteStop();
                stop.setLocationId("L" + stopIndex++);
                stop.setLatitude(location.getLatitude());
                stop.setLongitude(location.getLongitude());
                stop.setPlannedDemand(location.getDemand());
                stops.add(stop);
            }

            route.setStops(stops);
            route.setMetrics(calculateRouteMetrics(entry.getKey(), entry.getValue()));
            routes.add(route);
        }

        return routes;
    }

    private RouteMetrics calculateRouteMetrics(VehicleRoutingOptimizer.Vehicle vehicle, List<VehicleRoutingOptimizer.Location> locations) {
        RouteMetrics metrics = new RouteMetrics();
        metrics.setTotalDistance(vehicle.getCurrentDistance());
        metrics.setTotalDemand(vehicle.getCurrentLoad());
        metrics.setVehicleUtilization((double) vehicle.getCurrentLoad() / vehicle.getCapacity());
        // Estimated time based on average speed of 50 km/h
        metrics.setTotalTime((long)(vehicle.getCurrentDistance() * 1.2 * 3600));
        return metrics;
    }
}