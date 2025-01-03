package com.logistics.model;

import lombok.Data;

@Data
public class Vehicle {
    private String id;
    private int capacity;
    private double maxDistance;
    private TimeWindow availability;
}