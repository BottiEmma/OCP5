package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {
	
	@Test
    void testGettersAndSetters() {
        LoginRequest loginRequest = new LoginRequest();

        String testEmail = "test@example.com";
        String testPassword = "securepassword";

        loginRequest.setEmail(testEmail);
        loginRequest.setPassword(testPassword);

        assertEquals(testEmail, loginRequest.getEmail());
        assertEquals(testPassword, loginRequest.getPassword());
    }

}
