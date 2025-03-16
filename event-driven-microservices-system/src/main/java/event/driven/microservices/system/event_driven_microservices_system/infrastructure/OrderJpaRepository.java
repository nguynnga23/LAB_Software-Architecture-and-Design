package event.driven.microservices.system.event_driven_microservices_system.infrastructure;

import event.driven.microservices.system.event_driven_microservices_system.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
}
