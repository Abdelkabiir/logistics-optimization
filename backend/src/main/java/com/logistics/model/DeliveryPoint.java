package com.logistics.model;

import lombok.Data;

@Data
public class DeliveryPoint {
    private String id;
    private double latitude;
    private double longitude;
    private int demand;
    private TimeWindow timeWindow;
}