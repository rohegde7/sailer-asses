package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class HighDemandProduct extends BaseModel{
    // 1 : M
    @OneToOne
    private Product product;
    private int maxQuantity;
}
