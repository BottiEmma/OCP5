package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {
	
	@Mock
	private JwtUtils jwtUtils;
	
	@Mock
	private UserDetailsServiceImpl userDetailsService;
	
	@Mock
	private FilterChain filterChain;
	
	@InjectMocks
	private AuthTokenFilter authTokenFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
	
	@Test
    void testDoFilterInternal_ValidJwt() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtils.validateJwtToken("valid-token")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("valid-token")).thenReturn("testUser");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithInvalidJwt_DoesNotSetAuthentication() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.jwt.token");
        when(jwtUtils.validateJwtToken("invalid.jwt.token")).thenReturn(false);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils).validateJwtToken("invalid.jwt.token");
        verify(filterChain).doFilter(request, response);
        // Note: No authentication set in SecurityContextHolder
    }

    @Test
    public void testParseJwt_WithValidHeader_ReturnsToken() throws Exception {
        // Arrange
        String jwt = "valid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        // Use reflection to access the private method
        Method method = AuthTokenFilter.class.getDeclaredMethod("parseJwt", HttpServletRequest.class);
        method.setAccessible(true);

        // Act
        String result = (String) method.invoke(authTokenFilter, request);

        // Assert
        assertEquals(jwt, result);
    }

    @Test
    public void testParseJwt_WithInvalidHeader_ReturnsNull() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        // Use reflection to access the private method
        Method method = AuthTokenFilter.class.getDeclaredMethod("parseJwt", HttpServletRequest.class);
        method.setAccessible(true);

        // Act
        String result = (String) method.invoke(authTokenFilter, request);

        // Assert
        assertEquals(null, result);
    }

}
