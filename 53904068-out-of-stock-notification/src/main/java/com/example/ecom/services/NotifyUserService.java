package com.example.ecom.services;

import com.example.ecom.libraries.Sendgrid;
import com.example.ecom.models.Notification;
import com.example.ecom.models.NotificationStatus;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyUserService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ProductRepository productRepository;

    public void notifyUser(int productId) {
        Product product = productRepository.findById(productId).get();
        List<Notification> notifications = notificationRepository.findByProduct(product);

        for (Notification notification : notifications) {
            if (notification != null) {
                new Sendgrid().sendEmailAsync(
                        notification.getUser().getEmail(),
                        product.getName() + " back in stock!",
                        "Dear" + notification.getUser().getName() + ", " + product.getName() + " is now back in stock. Grab it ASAP!"
                );
                notification.setStatus(NotificationStatus.SENT);
                notificationRepository.save(notification);
            }
        }
    }
}
