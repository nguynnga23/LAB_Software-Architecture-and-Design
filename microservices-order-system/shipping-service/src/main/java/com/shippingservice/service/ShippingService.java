package com.shippingservice.service;

import com.shippingservice.entity.Shipment;
import com.shippingservice.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private OrderServiceClient orderServiceClient;

    public Shipment createShipment(Shipment shipment) {
        // Validate order existence
        if (orderServiceClient.getOrderById(shipment.getOrderId()) == null) {
            throw new IllegalArgumentException("Invalid order ID: " + shipment.getOrderId());
        }

        shipment.setStatus("PENDING");
        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipmentStatus(Long shipmentId, String status) {
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if (shipmentOptional.isPresent()) {
            Shipment shipment = shipmentOptional.get();
            shipment.setStatus(status);
            return shipmentRepository.save(shipment);
        } else {
            throw new IllegalArgumentException("Shipment not found with ID: " + shipmentId);
        }
    }

    public Shipment getShipmentById(Long shipmentId) {
        return shipmentRepository.findById(shipmentId).orElse(null);
    }
}
