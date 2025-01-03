package com.logistics.model;

import lombok.Data;
import java.util.List;

@Data
public class OptimizationRequest {
    private List<DeliveryPoint> deliveryPoints;
    private List<Vehicle> vehicles;
    private Location depot;
    private OptimizationConstraints constraints;
}