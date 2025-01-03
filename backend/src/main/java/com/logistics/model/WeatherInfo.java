package com.logistics.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    private String locationId;
    private WeatherCondition condition;
    private double temperature;
    private double windSpeed;
    private double precipitation;
    private double visibility;
    private long timestamp;
}

