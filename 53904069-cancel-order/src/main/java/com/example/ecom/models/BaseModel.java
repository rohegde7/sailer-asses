package com.example.ecom.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
}
