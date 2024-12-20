package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TeacherTest {

    @Test
    void testTeacherModel() {
        Teacher builtTeacher = Teacher.builder()
                .id(1L)
                .lastName("LastName")
                .firstName("FirstName")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(1L, builtTeacher.getId());
        assertEquals("LastName", builtTeacher.getLastName());
        assertEquals("FirstName", builtTeacher.getFirstName());
    }

    @Test
    void testHashCode_SameId() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .lastName("lastname")
                .firstName("firstname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            Teacher teacher2 = Teacher.builder()
                .id(1L)
                .lastName("lastname2")
                .firstName("firstname2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(teacher1.hashCode(), teacher2.hashCode(), "Hash codes should be equal for teacher with the same ID");
    }

    @Test
    void testHashCode_DifferentId() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .lastName("lastname")
                .firstName("firstname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            Teacher teacher2 = Teacher.builder()
                .id(2L)
                .lastName("lastname2")
                .firstName("firstname2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertNotEquals(teacher1.hashCode(), teacher2.hashCode(), "Hash codes should be different for sessions with different IDs");
    }

}
