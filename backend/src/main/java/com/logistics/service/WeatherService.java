package com.logistics.service;

import com.logistics.model.Location;
import com.logistics.model.WeatherInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class WeatherService {
    public Map<String, WeatherInfo> getWeatherForecast(List<Location> locations) {
        // Implementation for weather service
        return new HashMap<>();
    }
}
