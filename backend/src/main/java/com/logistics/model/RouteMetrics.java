package com.logistics.model;

import lombok.Data;

@Data
public class RouteMetrics {
    private double totalDistance;
    private int totalDemand;
    private long totalTime;
    private double vehicleUtilization;
}
