package com.example.eventmanagementdemo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        // Initialize a Role and User object before each test
        role = new Role(1, "Host");
        user = new User(1, "testUser", "password123", role);
    }

    @Test
    public void testGetId() {
        assertEquals(1, user.getId(), "User ID should be 1");
    }

    @Test
    public void testSetId() {
        user.setId(2);
        assertEquals(2, user.getId(), "User ID should be 2");
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername(), "Username should be 'testUser'");
    }

    @Test
    public void testSetUsername() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername(), "Username should be 'newUser'");
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword(), "Password should be 'password123'");
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword(), "Password should be 'newPassword'");
    }

    @Test
    public void testGetRole() {
        assertEquals(role, user.getRole(), "Role should be 'Host'");
    }

    @Test
    public void testSetRole() {
        Role newRole = new Role(2, "User");
        user.setRole(newRole);
        assertEquals(newRole, user.getRole(), "Role should be 'User'");
    }
}
