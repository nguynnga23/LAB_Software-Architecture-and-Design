package com.inventoryservice.service;

import com.inventoryservice.entity.Stock;
import com.inventoryservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private StockRepository stockRepository;

    public Stock getStockByProductId(Long productId) {
        return stockRepository.findById(productId).orElse(null);
    }

    public Stock updateStock(Long productId, int quantity) {
        Optional<Stock> stockOptional = stockRepository.findById(productId);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setQuantity(stock.getQuantity() + quantity);
            return stockRepository.save(stock);
        } else {
            throw new IllegalArgumentException("Stock not found for Product ID: " + productId);
        }
    }

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
