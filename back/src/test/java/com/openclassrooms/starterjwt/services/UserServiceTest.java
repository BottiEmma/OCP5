package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	UserService userService;
	
	@BeforeEach
	public void init() {
    	userService = new UserService(userRepository);
	}
	
	@Test
    public void testDelete() {
    	Long id = 1L;

        userService.delete(id);
        
        verify(userRepository, times(1)).deleteById(id);
    }
	
	@Test
    void testFindById() {
        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_UserDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User foundUser = userService.findById(1L);

        assertNull(foundUser);
    }

}
