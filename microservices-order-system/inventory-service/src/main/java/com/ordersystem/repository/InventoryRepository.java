package com.ordersystem.repository;

import com.ordersystem.entity.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
}
