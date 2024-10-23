package com.example.eventmanagementdemo.dao.role;

import com.example.eventmanagementdemo.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleDAO {
    Optional<Role> getRoleByName(String name);

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Integer id);
}

