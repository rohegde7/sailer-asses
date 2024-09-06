package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User signupUser(String name, String email, String password) throws Exception {
        if(userRepository.existsByEmail(email)) throw new RuntimeException("Email already exists");

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        User user = userRepository.getByEmail(email);
        if(user == null) throw new RuntimeException("User not found");

        return new BCryptPasswordEncoder().matches(password, user.getPassword());
    }
}
