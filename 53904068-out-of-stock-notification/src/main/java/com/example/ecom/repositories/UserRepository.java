package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
