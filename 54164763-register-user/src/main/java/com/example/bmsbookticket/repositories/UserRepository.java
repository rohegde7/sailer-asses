package com.example.bmsbookticket.repositories;


import com.example.bmsbookticket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    User getByEmail(String email);
}
