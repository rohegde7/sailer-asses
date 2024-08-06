package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}

