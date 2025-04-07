package event.driven.microservices.system.event_driven_microservices_system.domain.repository;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}