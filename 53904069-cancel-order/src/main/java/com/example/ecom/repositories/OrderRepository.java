package com.example.ecom.repositories;

import com.example.ecom.models.Order;
import com.example.ecom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Integer> {

}
