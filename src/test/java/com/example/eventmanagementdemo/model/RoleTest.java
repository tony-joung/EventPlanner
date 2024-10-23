package com.example.eventmanagementdemo.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        // Initialize a Role object before each test
        role = new Role(1, "Host");
    }

    @Test
    public void testGetId() {
        assertEquals(1, role.getId(), "Role ID should be 1");
    }

    @Test
    public void testSetId() {
        role.setId(2);
        assertEquals(2, role.getId(), "Role ID should be 2");
    }

    @Test
    public void testGetName() {
        assertEquals("Host", role.getName(), "Role name should be 'Host'");
    }

    @Test
    public void testSetName() {
        role.setName("User");
        assertEquals("User", role.getName(), "Role name should be 'User'");
    }

    @Test
    public void testToString() {
        assertEquals("Host", role.toString(), "toString should return the role name 'Host'");
    }
}
