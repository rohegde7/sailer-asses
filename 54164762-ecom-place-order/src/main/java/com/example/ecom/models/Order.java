package com.example.ecom.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "orders")
public class Order extends BaseModel{
    @ManyToOne
    // M : 1
    private User user;
    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderDetail> orderDetails;

    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus orderStatus;
}
