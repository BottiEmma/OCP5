package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    SessionRepository sessionRepository;

    @Mock
    UserRepository userRepository;
    
    SessionService sessionService;
    
    @BeforeEach
	public void init() {
    	sessionService = new SessionService(sessionRepository, userRepository);
	}

    @Test
    public void testCreate() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session result = sessionService.create(session);

        assertEquals(session, result);
        verify(sessionRepository).save(session);
    }
    
    @Test
    public void testDelete() {
    	Long id = 1L;

        sessionService.delete(id);
        
        verify(sessionRepository, Mockito.times(1)).deleteById(id);
    }
    
    @Test
    void testFindAll() {
        List<Session> sessions = List.of(new Session(), new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> result = sessionService.findAll();

        assertEquals(sessions, result);
        verify(sessionRepository, Mockito.times(1)).findAll();
    }
    
    @Test
    void testGetById() {
        Long id = 1L;
        Session session = new Session();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));

        Session result = sessionService.getById(id);

        assertEquals(session, result);
        verify(sessionRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testParticipateSuccess() {
        Long sessionId = 1L;
        Long userId = 2L;
        Session session = new Session();
        session.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(userId);

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(sessionRepository.save(session)).thenReturn(session);

        sessionService.participate(sessionId, userId);

        assertTrue(session.getUsers().contains(user));
        Mockito.verify(sessionRepository).save(session);
    }

    @Test
    void testNoLongerParticipateSuccess() {
        Long sessionId = 1L;
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        Session session = new Session();
        session.setUsers(new ArrayList<>(List.of(user)));

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        Mockito.when(sessionRepository.save(session)).thenReturn(session);

        sessionService.noLongerParticipate(sessionId, userId);

        assertFalse(session.getUsers().contains(user));
        Mockito.verify(sessionRepository).save(session);
    }

    @Test
    void testParticipate_AlreadyParticipates() {
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        session.setUsers(Arrays.asList(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> {
            sessionService.participate(1L, 1L);
        });

        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    void testNoLongerParticipate_SessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.noLongerParticipate(1L, 1L);
        });

        verify(sessionRepository, times(1)).findById(1L);
        verify(sessionRepository, never()).save(any(Session.class));
    }
  

}