package com.example.ecom.services;

import com.example.ecom.exceptions.OrderCannotBeCancelledException;
import com.example.ecom.exceptions.OrderDoesNotBelongToUserException;
import com.example.ecom.exceptions.OrderNotFoundException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;


    public Order cancelOrder(int orderId, int userId) throws UserNotFoundException, OrderNotFoundException, OrderDoesNotBelongToUserException, OrderCannotBeCancelledException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getUser().getId() != user.getId()) {
            throw new OrderDoesNotBelongToUserException("Order does not belong to user");
        }

//        if (order.getOrderStatus() == OrderStatus.DELIVERED
//                || order.getOrderStatus() == OrderStatus.CANCELLED
//                || order.getOrderStatus() == OrderStatus.SHIPPED) {
        if (order.getOrderStatus() != OrderStatus.PLACED) {
            throw new OrderCannotBeCancelledException("Order cannot be cancelled");
        }


        orderDetailRepository.findByOrderId(orderId).forEach(orderDetail -> {
                    Product product = productRepository.findById(orderDetail.getProduct().getId()).get();
                    OrderDetail orderDetail1 = orderDetailRepository.findById(orderDetail.getId()).get();
                    Inventory inventory = inventoryRepository.findByProductName(orderDetail.getProduct().getName());
                    inventory.setQuantity(inventory.getQuantity() + 1);
                    inventoryRepository.save(inventory);
                }
        );

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return order;
    }
}
