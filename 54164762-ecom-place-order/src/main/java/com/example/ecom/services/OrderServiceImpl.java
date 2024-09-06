package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    HighDemandProductRepository highDemandProductRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Order placeOrder(int userId, int addressId, List<Pair<Integer, Integer>> orderDetails)
            throws UserNotFoundException, InvalidAddressException, OutOfStockException, InvalidProductException, HighDemandProductException {
//        List<Address> addressList = addressRepository.findAll();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new InvalidAddressException("No Address"));
        if (address.getUser().getId() != userId)
            throw new UserNotFoundException("User not found"); // address does not belong to user

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setDeliveryAddress(address);
        orderRepository.save(order);

        for (Pair<Integer, Integer> orderDetail : orderDetails) {
            int productId = orderDetail.getFirst();
            int quantity = orderDetail.getSecond();

            Product product = productRepository.findById(productId).orElseThrow(() -> new InvalidProductException("Product not found"));
            Inventory inventory = inventoryRepository.findByProductId(productId);
            if (inventory.getQuantity() < quantity) throw new OutOfStockException("Out of stock");

            HighDemandProduct highDemandProduct = highDemandProductRepository.findByProductId(productId);
            if (highDemandProduct != null && quantity > highDemandProduct.getMaxQuantity())
                throw new HighDemandProductException("Jyada order kar diya bhai tune");

            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);

            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setProduct(product);
            orderDetail1.setQuantity(quantity);
            orderDetail1.setOrder(order);
            orderDetailList.add(orderDetail1);
            orderDetailRepository.save(orderDetail1);
        }

        order.setOrderDetails(orderDetailList);
        orderRepository.save(order);

        return orderRepository.save(order);
    }
}
