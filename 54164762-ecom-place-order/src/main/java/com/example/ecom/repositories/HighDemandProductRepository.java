package com.example.ecom.repositories;


import com.example.ecom.models.Address;
import com.example.ecom.models.HighDemandProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighDemandProductRepository extends JpaRepository<HighDemandProduct, Integer> {
    HighDemandProduct findByProductId(Integer productId);
}
