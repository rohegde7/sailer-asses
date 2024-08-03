package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
public class OrderDetail extends BaseModel{
    @ManyToOne
    @ToString.Exclude
    private Order order;
    @OneToOne
    @ToString.Exclude
    private Product product;
    private int quantity;
}
