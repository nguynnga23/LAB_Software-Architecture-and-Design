package event.driven.microservices.system.event_driven_microservices_system.infrastructure.persistence;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
}
