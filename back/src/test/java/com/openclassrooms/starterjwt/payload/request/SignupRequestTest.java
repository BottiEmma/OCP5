package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SignupRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
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

    @Test
    void testValidation_Success() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid@example.com");
        signupRequest.setFirstName("FirstName");
        signupRequest.setLastName("LastName");
        signupRequest.setPassword("StrongPass123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void testValidation_Failure() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("invalid-email"); 
        signupRequest.setFirstName(""); 
        signupRequest.setLastName("LN"); 
        signupRequest.setPassword("123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertFalse(violations.isEmpty(), "Expected validation errors");

        for (ConstraintViolation<SignupRequest> violation : violations) {
            System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
        }
    }

    @Test
    void testEqualsAndHashCode() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("FirstName");
        request1.setLastName("LastName");
        request1.setPassword("password");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("FirstName");
        request2.setLastName("LastName");
        request2.setPassword("password");

        assertEquals(request1, request2, "Requests should be equal");
        assertEquals(request1.hashCode(), request2.hashCode(), "Hash codes should match");
    }

    @Test
    void testToString() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("FirstName");
        signupRequest.setLastName("LastName");
        signupRequest.setPassword("password");

        String expected = "SignupRequest(email=test@example.com, firstName=FirstName, lastName=LastName, password=password)";
        assertEquals(expected, signupRequest.toString());
    }

}
