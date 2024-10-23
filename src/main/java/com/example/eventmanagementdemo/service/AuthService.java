package com.example.eventmanagementdemo.service;

import com.example.eventmanagementdemo.dao.user.IUserDAO;
import com.example.eventmanagementdemo.model.User;
import com.example.eventmanagementdemo.utils.PasswordHasher;

import java.util.Optional;

public class AuthService {
    private IUserDAO userDAO;

    public AuthService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password, String roleName) {
        Optional<User> userOpt = userDAO.getUserByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String storedHashedPassword = user.getPassword(); // This is the hashed password stored in the database
            // Hash the input password
            String inputHashedPassword = PasswordHasher.hashPassword(password);
            // Compare the stored hashed password with the hashed input password
            return storedHashedPassword.equals(inputHashedPassword) && user.getRole().getName().equals(roleName);
        }
        return false;
    }
}
