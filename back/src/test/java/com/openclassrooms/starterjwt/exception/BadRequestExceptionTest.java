package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BadRequestExceptionTest {
    @Test
    void testBadRequestException() {
        BadRequestException exception = new BadRequestException();
        assertNotNull(exception);

        assertTrue(exception instanceof RuntimeException);
    }

}
