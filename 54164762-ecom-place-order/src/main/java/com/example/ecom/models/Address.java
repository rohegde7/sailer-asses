package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Address extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    private String building;
    private int floor;
    private String roomNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
