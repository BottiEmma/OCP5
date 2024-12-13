package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
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

}
