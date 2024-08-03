package com.example.ecom.repositories;

import com.example.ecom.models.OrderDetail;
import com.example.ecom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(int orderId);
}
