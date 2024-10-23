package com.example.eventmanagementdemo.dao.user;

import com.example.eventmanagementdemo.model.User;

import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUserByUsername(String username);

    boolean saveUser(User user);
}
