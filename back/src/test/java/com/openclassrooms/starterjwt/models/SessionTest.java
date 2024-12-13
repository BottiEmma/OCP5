package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SessionTest {
	
     @Test
    void testSession() {
        Teacher teacher = new Teacher();
        User user1 = new User();
        User user2 = new User();

        Date sessionDate = new Date();
        String sessionName = "session";
        String sessionDescription = "description";

        Session session = Session.builder()
                .id(1L)
                .name(sessionName)
                .date(sessionDate)
                .description(sessionDescription)
                .teacher(teacher)
                .users(List.of(user1, user2))
                .build();

        assertEquals(1L, session.getId());
        assertEquals(sessionName, session.getName());
        assertEquals(sessionDate, session.getDate());
        assertEquals(sessionDescription, session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(2, session.getUsers().size());
    }
	

}