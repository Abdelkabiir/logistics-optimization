package com.logistics.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationResponse {
    private List<Route> routes;
    private OptimizationMetrics metrics;
    private String optimizationId;
}