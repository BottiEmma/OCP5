package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SignupRequestTest {
	
	@Test
    void testGettersAndSetters() {
        SignupRequest signupRequest = new SignupRequest();
        String testEmail = "test@example.com";
        String testFirstName = "FirstName";
        String testLastName = "LastName";
        String testPassword = "password";

        signupRequest.setEmail(testEmail);
        signupRequest.setFirstName(testFirstName);
        signupRequest.setLastName(testLastName);
        signupRequest.setPassword(testPassword);

        assertEquals(testEmail, signupRequest.getEmail());
        assertEquals(testFirstName, signupRequest.getFirstName());
        assertEquals(testLastName, signupRequest.getLastName());
        assertEquals(testPassword, signupRequest.getPassword());
    }

}
