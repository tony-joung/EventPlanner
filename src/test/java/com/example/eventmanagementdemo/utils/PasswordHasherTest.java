package com.example.eventmanagementdemo.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Base64;

public class PasswordHasherTest {

    @Test
    public void testHashPassword() {
        String password = "mySecurePassword";
        String hashedPassword = PasswordHasher.hashPassword(password);

        // Check that the hashed password is not null
        assertNotNull(hashedPassword);

        // Check that hashing the same password returns the same hash
        String hashedPasswordAgain = PasswordHasher.hashPassword(password);
        assertEquals(hashedPassword, hashedPasswordAgain);

        // Check that hashing a different password gives a different hash
        String differentPassword = "myDifferentPassword";
        String differentHashedPassword = PasswordHasher.hashPassword(differentPassword);
        assertNotEquals(hashedPassword, differentHashedPassword);
    }

    @Test
    public void testGenerateSalt() {
        String salt1 = PasswordHasher.generateSalt();
        String salt2 = PasswordHasher.generateSalt();

        // Check that the salt is not null
        assertNotNull(salt1);

        // Check that the salt is of expected length
        assertEquals(24, salt1.length()); // Base64 encoding of 16 bytes

        // Check that two salts are different
        assertNotEquals(salt1, salt2);

        // Check that the salt is valid Base64
        assertDoesNotThrow(() -> Base64.getDecoder().decode(salt1));
    }
}
