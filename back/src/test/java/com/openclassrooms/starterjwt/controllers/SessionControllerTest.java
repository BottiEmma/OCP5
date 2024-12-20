package com.openclassrooms.starterjwt.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.openclassrooms.starterjwt.services.SessionService;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private UserRepository userRepository;

    private Session session;

    @BeforeEach
    void setUp() {
        session = Session.builder()
                .id(2L)
                .name("test")
                .description("desc")
                .build();
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById_SessionExists() throws Exception {
        Mockito.when(sessionService.getById(2L)).thenReturn(session);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"test\"}"));
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById_SessionDoesNotExist() throws Exception {
        when(sessionService.getById(99L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindById_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindAll() throws Exception {
        List<Session> sessions = List.of(session);
        Mockito.when(sessionService.findAll()).thenReturn(sessions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"test\",\"description\":\"desc\"}]"));
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testCreate() throws Exception {
        Mockito.when(sessionService.create(any(Session.class))).thenReturn(session);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                        + "\"name\":\"test\","
                        + "\"date\":\"2024-12-13T10:00:00\","
                        + "\"teacher_id\":1,"
                        + "\"description\":\"desc\","
                        + "\"users\":[2, 3]"
                        + "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"test\",\"description\":\"desc\"}"));
    }

    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testUpdate() throws Exception {
        Mockito.when(sessionService.update(Mockito.eq(2L), any(Session.class))).thenReturn(session);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                            + "\"name\":\"test\","
                            + "\"date\":\"2024-12-13T10:00:00\","
                            + "\"teacher_id\":1,"
                            + "\"description\":\"desc update\","
                            + "\"users\":[2, 3]"
                            + "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"test\",\"description\":\"desc\"}"));
    }


    @Test
    @WithMockUser(username = "john.doe@example.com")
    void testParticipate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/2/participate/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john.doe@example.com")
    void testNoLongerParticipate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/2/participate/1"))
                .andExpect(status().isOk());
    }

    
    
}
