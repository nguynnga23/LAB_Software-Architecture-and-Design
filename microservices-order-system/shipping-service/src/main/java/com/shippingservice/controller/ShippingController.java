package com.shippingservice.controller;

import com.shippingservice.entity.Shipment;
import com.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {
    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public Shipment createShipment(@RequestBody Shipment shipment) {
        return shippingService.createShipment(shipment);
    }

    @PutMapping("/{shipmentId}")
    public Shipment updateShipmentStatus(@PathVariable Long shipmentId, @RequestParam String status) {
        return shippingService.updateShipmentStatus(shipmentId, status);
    }

    @GetMapping("/{shipmentId}")
    public Shipment getShipmentById(@PathVariable Long shipmentId) {
        return shippingService.getShipmentById(shipmentId);
    }
}
