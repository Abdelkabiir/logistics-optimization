package com.logistics.model;

import lombok.Data;

@Data
public class RouteStop {
    private String locationId;
    private double latitude;
    private double longitude;
    private int sequenceNumber;
    private long estimatedArrival;
    private int plannedDemand;
}