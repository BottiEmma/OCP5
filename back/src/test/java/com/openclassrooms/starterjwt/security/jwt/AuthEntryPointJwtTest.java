package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class AuthEntryPointJwtTest {
	@Autowired
    private AuthEntryPointJwt authEntryPointJwt;

     @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	// Integration
    @Test
    void testUnauthorizedAccess() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/secure-endpoint");

        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException authException = new AuthenticationException("Invalid credentials") {
        };

        authEntryPointJwt.commence(request, response, authException);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> responseBody = new ObjectMapper().readValue(response.getContentAsByteArray(), Map.class);
        assertThat(responseBody).containsEntry("status", HttpServletResponse.SC_UNAUTHORIZED);
        assertThat(responseBody).containsEntry("error", "Unauthorized");
        assertThat(responseBody).containsEntry("message", "Invalid credentials");
        assertThat(responseBody).containsEntry("path", "/secure-endpoint");
    }

    @Test
    public void testCommence() throws IOException, ServletException {
        // Arrange
        when(authException.getMessage()).thenReturn("Unauthorized access");

        // Utilisation de ByteArrayOutputStream pour capturer le flux de sortie
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // Rien à implémenter pour ce test
            }

            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);
        when(request.getServletPath()).thenReturn("/test-path");

        // Act
        authEntryPointJwt.commence(request, response, authException);

        // Assert
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Vérification du corps de la réponse
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(byteArrayOutputStream.toString(), HashMap.class);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseMap.get("status"));
        assertEquals("Unauthorized", responseMap.get("error"));
        assertEquals("Unauthorized access", responseMap.get("message"));
        assertEquals("/test-path", responseMap.get("path"));
    }

}
