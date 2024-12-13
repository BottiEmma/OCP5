package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testUserModel() {
        User builtUser = User.builder()
                .id(1L)
                .email("example@example.com")
                .lastName("LastName")
                .firstName("FirstName")
                .password("password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(1L, builtUser.getId());
        assertEquals("example@example.com", builtUser.getEmail());
        assertEquals("LastName", builtUser.getLastName());
        assertEquals("FirstName", builtUser.getFirstName());
        assertEquals("password", builtUser.getPassword());
        assertFalse(builtUser.isAdmin());
    }

}
