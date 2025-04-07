package event.driven.microservices.system.event_driven_microservices_system.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Data
@Setter
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;
    private String customerName;
    private String productName;
    private int quantity;
    private String status; // e.g., PENDING, COMPLETED

    public Order(UUID id, String customerName, String productName, int quantity, String status) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
    }

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}