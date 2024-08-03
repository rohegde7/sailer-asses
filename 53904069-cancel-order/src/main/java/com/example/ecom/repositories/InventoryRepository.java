package com.example.ecom.repositories;


import com.example.ecom.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Inventory findByProductName(String productName);
}
