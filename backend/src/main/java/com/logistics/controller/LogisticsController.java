package com.logistics.controller;

import com.logistics.model.*;
import com.logistics.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logistics")
@CrossOrigin(origins = "*")
public class LogisticsController {
    private final RoutingService routingService;

    public LogisticsController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizationResponse> optimizeRoutes(@RequestBody OptimizationRequest request) {
        return ResponseEntity.ok(routingService.optimizeRoutes(request));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<Route> getRouteDetails(@PathVariable String routeId) {
        return ResponseEntity.ok(routingService.getRouteDetails(routeId));
    }
}