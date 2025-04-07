package event.driven.microservices.system.event_driven_microservices_system.application.service;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Stock;
import event.driven.microservices.system.event_driven_microservices_system.domain.repository.StockRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final StockRepository stockRepository;

    public InventoryService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @KafkaListener(topics = "order-placed", groupId = "inventory-group")
    public void updateStock(String message) {
        // Parse the message
        String productName = extractProductName(message); // Extract product name
        int quantity = extractQuantity(message);         // Extract quantity

        // Fetch stock
        Stock stock = stockRepository.findById(productName)
                .orElse(null);

        if (stock == null) {
            System.err.println("Product not found in inventory: " + productName);
            return; // Exit without crashing
        }

        // Update stock
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        System.out.println("Stock updated for product: " + productName);
    }

    private String extractProductName(String message) {
        // Placeholder: parse product name from JSON (use Jackson or manual parsing)
        return "Laptop";
    }

    private int extractQuantity(String message) {
        // Placeholder: parse quantity from JSON
        return 1;
    }
}
