package com.ordersystem.controller;

import com.ordersystem.entity.InventoryItem;
import com.ordersystem.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryItem> getAllItems() {
        return inventoryService.getAllItems();
    }

    @GetMapping("/{id}")
    public InventoryItem getItemById(@PathVariable String id) {
        return inventoryService.getItemById(id);
    }

    @PostMapping
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        return inventoryService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        inventoryService.deleteItem(id);
    }
}
