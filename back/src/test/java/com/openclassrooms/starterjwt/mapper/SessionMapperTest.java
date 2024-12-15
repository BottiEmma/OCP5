package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

public class SessionMapperTest {
	
	@InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Test session description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(2L, 3L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(2L)).thenReturn(user1);
        when(userService.findById(3L)).thenReturn(user2);

        Session session = sessionMapper.toEntity(sessionDto);

        assertNotNull(session);
        assertEquals("Test session description", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(1L, session.getTeacher().getId());
        assertEquals(2, session.getUsers().size());
        assertEquals(2L, session.getUsers().get(0).getId());
        assertEquals(3L, session.getUsers().get(1).getId());
        verify(teacherService).findById(1L);
        verify(userService).findById(2L);
        verify(userService).findById(3L);
    }

    @Test
    void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        Session session = new Session();
        session.setDescription("Test session description");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertNotNull(sessionDto);
        assertEquals("Test session description", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        assertEquals(2, sessionDto.getUsers().size());
        assertTrue(sessionDto.getUsers().contains(2L));
        assertTrue(sessionDto.getUsers().contains(3L));
    }

    @Test
    public void testToEntityList() throws ParseException {
        SessionDto dto1 = new SessionDto();
        dto1.setId(1L);
        dto1.setName("Session 1");
        dto1.setDescription("Description 1");
        dto1.setDate(dateFormat.parse("2024-08-20T21:33:08"));
        dto1.setCreatedAt(LocalDateTime.parse("2024-08-20T21:33:08"));
        dto1.setUpdatedAt(LocalDateTime.parse("2024-08-20T21:33:08"));
        dto1.setTeacher_id(2L);
        dto1.setUsers(Arrays.asList(3L, 4L));

        SessionDto dto2 = new SessionDto();
        dto2.setId(2L);
        dto2.setName("Session 2");
        dto2.setDescription("Description 2");
        dto2.setDate(dateFormat.parse("2024-08-21T21:33:08"));
        dto2.setCreatedAt(LocalDateTime.parse("2024-08-21T21:33:08"));
        dto2.setUpdatedAt(LocalDateTime.parse("2024-08-21T21:33:08"));
        dto2.setTeacher_id(3L);
        dto2.setUsers(Collections.singletonList(5L));

        Teacher teacher1 = new Teacher();
        teacher1.setId(2L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(3L);

        when(teacherService.findById(2L)).thenReturn(teacher1);
        when(teacherService.findById(3L)).thenReturn(teacher2);

        User user1 = new User();
        user1.setId(3L);
        User user2 = new User();
        user2.setId(4L);
        User user3 = new User();
        user3.setId(5L);

        when(userService.findById(3L)).thenReturn(user1);
        when(userService.findById(4L)).thenReturn(user2);
        when(userService.findById(5L)).thenReturn(user3);

        List<SessionDto> dtoList = Arrays.asList(dto1, dto2);

        List<Session> sessionList = sessionMapper.toEntity(dtoList);

        assertEquals(dtoList.size(), sessionList.size());
        assertEquals(dtoList.get(0).getId(), sessionList.get(0).getId());
        assertEquals(dtoList.get(1).getId(), sessionList.get(1).getId());
    }

    @Test
    public void testToDtoList() throws ParseException {
        Teacher teacher1 = new Teacher();
        teacher1.setId(2L);

        User user1 = new User();
        user1.setId(3L);
        User user2 = new User();
        user2.setId(4L);

        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Session 1");
        session1.setDescription("Description 1");
        session1.setDate(dateFormat.parse("2024-08-20T21:33:08"));
        session1.setCreatedAt(LocalDateTime.parse("2024-08-20T21:33:08"));
        session1.setUpdatedAt(LocalDateTime.parse("2024-08-20T21:33:08"));
        session1.setTeacher(teacher1);
        session1.setUsers(Arrays.asList(user1, user2));

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Session 2");
        session2.setDescription("Description 2");
        session2.setDate(dateFormat.parse("2024-08-21T21:33:08"));
        session2.setCreatedAt(LocalDateTime.parse("2024-08-21T21:33:08"));
        session2.setUpdatedAt(LocalDateTime.parse("2024-08-21T21:33:08"));
        session2.setTeacher(null);
        session2.setUsers(Collections.singletonList(user2));

        List<Session> sessionList = Arrays.asList(session1, session2);

        List<SessionDto> dtoList = sessionMapper.toDto(sessionList);

        assertEquals(sessionList.size(), dtoList.size());
        assertEquals(sessionList.get(0).getId(), dtoList.get(0).getId());
        assertEquals(sessionList.get(1).getId(), dtoList.get(1).getId());
    }

}
