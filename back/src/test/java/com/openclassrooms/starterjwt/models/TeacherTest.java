package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
