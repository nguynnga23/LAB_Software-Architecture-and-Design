package com.inventoryservice.controller;

import com.inventoryservice.entity.Stock;
import com.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public Stock getStockByProductId(@PathVariable Long productId) {
        return inventoryService.getStockByProductId(productId);
    }

    @PutMapping("/{productId}")
    public Stock updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        return inventoryService.updateStock(productId, quantity);
    }

    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return inventoryService.addStock(stock);
    }
}
