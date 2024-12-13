package com.openclassrooms.starterjwt.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessageResponseTest {
	
	@Test
    void testGetMessage() {
        String testMessage = "This is a test message";

        MessageResponse messageResponse = new MessageResponse(testMessage);

        assertEquals(testMessage, messageResponse.getMessage());
    }
	
	@Test
    void testSetMessage() {
        MessageResponse messageResponse = new MessageResponse(null);

        String updatedMessage = "Updated message";
        messageResponse.setMessage(updatedMessage);

        assertEquals(updatedMessage, messageResponse.getMessage());
    }

}
