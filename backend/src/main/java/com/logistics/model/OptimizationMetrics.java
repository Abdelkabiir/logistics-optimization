package com.logistics.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationMetrics {
    private double totalDistance;         // Total distance covered by all routes
    private int totalDeliveries;          // Total number of delivery points served
    private int vehiclesUsed;             // Number of vehicles used in solution
    private double averageUtilization;    // Average vehicle capacity utilization
    private double totalCost;             // Total operational cost
    private long totalTime;               // Total time for all deliveries
}