package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Notification extends BaseModel{
    @OneToOne
    private Product product;
    @OneToOne
    private User user;

    @Enumerated(value = EnumType.ORDINAL)
    private NotificationStatus status;
}
