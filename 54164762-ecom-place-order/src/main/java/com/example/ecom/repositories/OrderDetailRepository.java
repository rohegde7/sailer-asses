package com.example.ecom.repositories;


import com.example.ecom.models.Address;
import com.example.ecom.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer> {
}
