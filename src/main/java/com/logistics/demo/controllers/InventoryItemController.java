package com.logistics.demo.controllers;

import java.sql.SQLException;
import java.util.List;

import com.logistics.demo.models.InventoryItem;
import com.logistics.demo.services.InventoryItemService;

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
@RequestMapping("inventory-items")
public class InventoryItemController {
    
    @Autowired
    private InventoryItemService inventoryItemService;

    @PostMapping("/create")
    public ResponseEntity<?> createInventoryItem(@RequestBody InventoryItem item) {
        try {
            inventoryItemService.createInventoryItem(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new inventory-item");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllInventoryItems() {
        try {
            List<InventoryItem> items = inventoryItemService.getAllInventoryItems();
            return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInventoryItem(@RequestBody InventoryItem item) {
        try {
            inventoryItemService.updateInventoryItem(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully updated a item with id: " + item.getItemId());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteInventoryItem(@PathVariable int id) {
        try {
            inventoryItemService.deleteInventoryItem(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted a item with id: " + id);
    }
}
