package event.driven.microservices.system.event_driven_microservices_system.infrastructure.persistence;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}