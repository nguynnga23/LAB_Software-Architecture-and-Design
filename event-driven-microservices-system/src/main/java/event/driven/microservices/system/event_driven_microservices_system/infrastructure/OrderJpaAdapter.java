package event.driven.microservices.system.event_driven_microservices_system.infrastructure;

import event.driven.microservices.system.event_driven_microservices_system.domain.Order;
import event.driven.microservices.system.event_driven_microservices_system.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OrderJpaAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    public OrderJpaAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveOrder(Order order) {
        jpaRepository.save(order);
    }

    @Override
    public Order findOrderById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }
}
