package com.logistics.demo.controllers;

import java.sql.SQLException;
import java.util.List;

import com.logistics.demo.models.InventoryItem;
import com.logistics.demo.models.Shipment;
import com.logistics.demo.services.InventoryItemService;
import com.logistics.demo.services.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/shipments")
public class ShipmentController {
    
    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private InventoryItemService inventoryItemService;

    @PostMapping("/create")
    public ResponseEntity<?> createShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.createShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new shipment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllShipments() {
        try {
            List<Shipment> shipments = shipmentService.getAllShipments();
            return new ResponseEntity<>(shipments, HttpStatus.OK);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{shipmentId}")
    public ResponseEntity<?> deleteShipmentById(@PathVariable int shipmentId) {
        try {
            shipmentService.deleteShipment(shipmentId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted a shipment with id: " + shipmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addToShipment/{shipmentId}")
    public ResponseEntity<?> addToShipment(@PathVariable int shipmentId, @RequestBody InventoryItem item) {
        try {

            // check to see that the item has enough quantity to be added to shipment
            InventoryItem existingItem = inventoryItemService.getInventoryItem(item.getItemId());
            
            if (existingItem.getQuantity() < item.getQuantity()) {
                throw new Exception("Not enough in stock for item to be added to shipment");
            }

            shipmentService.addToShipment(shipmentId, item.getItemId(), item.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added a new inventory-item");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
