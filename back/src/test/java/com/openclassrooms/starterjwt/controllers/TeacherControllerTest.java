package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

// Test d'intégration
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = Teacher.builder()
                .id(1L)
                .lastName("LastName")
                .firstName("FirstName")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
                when(teacherService.findById(1L)).thenReturn(teacher);
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"lastName\":\"LastName\",\"firstName\":\"FirstName\"}"));
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById_TeacherDoesNotExist() throws Exception {
        when(teacherService.findById(99L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachr/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindAll() throws Exception {
        List<Teacher> teachers = List.of(teacher);
        Mockito.when(teacherService.findAll()).thenReturn(teachers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"lastName\":\"LastName\",\"firstName\":\"FirstName\"}]"));
    }
    
}
