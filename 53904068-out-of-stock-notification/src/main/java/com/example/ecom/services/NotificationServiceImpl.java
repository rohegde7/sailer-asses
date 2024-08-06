package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private NotificationService notificationService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct(product).get();

        if(inventory.getQuantity() != 0) throw new ProductInStockException("Product is already in stock");

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setProduct(product);
        notification.setStatus(NotificationStatus.PENDING);
        return notificationRepository.save(notification);
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        if(notification.getUser().getId() != user.getId()) throw new UnAuthorizedException("You are not allowed to deregister notifications");

        notificationRepository.delete(notification);
    }
}
