package com.logistics.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationConstraints {
    private int maxRouteTime;             // Maximum allowed time for a single route (minutes)
    private boolean allowPartialDeliveries; // Whether to allow splitting deliveries
    private double maxDistance;           // Maximum distance per vehicle
    private List<String> priorityCustomers; // Customers that must be served first
    private int maxStopsPerRoute;         // Maximum number of stops per vehicle
    private TimeWindow globalTimeWindow;   // Global time window for all deliveries
    private double maxWaitingTime;        // Maximum waiting time at each stop
    private boolean respectVehicleRegions; // Whether to respect vehicle service regions
}