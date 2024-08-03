package com.example.ecom.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity(name = "orders")
public class Order extends BaseModel{
    @ManyToOne
    @ToString.Exclude
    private User user;
    @OneToMany
    @ToString.Exclude
    private List<OrderDetail> orderDetails;
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus orderStatus;
}
