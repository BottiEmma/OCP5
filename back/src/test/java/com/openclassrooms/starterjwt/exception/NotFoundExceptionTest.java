package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {

    @Test
    void testNotFoundException() {
        NotFoundException exception = new NotFoundException();
        assertNotNull(exception);

        assertTrue(exception instanceof RuntimeException);
    }

}
