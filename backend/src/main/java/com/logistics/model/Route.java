package com.logistics.model;

import lombok.Data;
import java.util.List;

@Data
public class Route {
    private String id;
    private String optimizationId;
    private String vehicleId;
    private List<RouteStop> stops;
    private RouteMetrics metrics;
}