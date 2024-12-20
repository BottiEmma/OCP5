package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Test
    void testHashCode_SameId() {
        User user1 = User.builder()
                .id(1L)
                .email("user1@example.com")
                .lastName("lastname")
                .firstName("firstname")
                .password("password123")
                .admin(false)
                .build();

        User user2 = User.builder()
                .id(1L) // Same ID
                .email("user2@example.com")
                .lastName("lastname2")
                .firstName("firstname2")
                .password("password456")
                .admin(true)
                .build();

        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be equal for users with the same ID");
    }

    @Test
    void testHashCode_DifferentId() {
        User user1 = User.builder()
                .id(1L)
                .email("user1@example.com")
                .lastName("lastname")
                .firstName("firstname")
                .password("password123")
                .admin(false)
                .build();

        User user2 = User.builder()
                .id(2L) // Different ID
                .email("user2@example.com")
                .lastName("lastname2")
                .firstName("firstname2")
                .password("password456")
                .admin(true)
                .build();

        assertNotEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be different for users with different IDs");
    }

    @Test
    void testToString() {
        LocalDateTime createdAt = LocalDateTime.of(2024, 12, 20, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 12, 21, 15, 30);
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("securepassword")
                .admin(true)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        String expectedToString = "User(id=1, email=test@example.com, lastName=Doe, firstName=John, password=securepassword, admin=true, createdAt=2024-12-20T10:00, updatedAt=2024-12-21T15:30)";

        assertEquals(expectedToString, user.toString());
    }

}
