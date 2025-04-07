package event.driven.microservices.system.event_driven_microservices_system.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Stock {
    @Id
    private String productName; // Unique product name
    private int quantity;       // Available stock

    public Stock() {
    }

    public Stock(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
