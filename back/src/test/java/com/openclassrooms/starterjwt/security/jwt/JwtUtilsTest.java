package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilsTest {
	
	@Mock
	private Authentication authentication;
	
	@Spy
	private JwtUtils jwtUtils;
	
	private final String jwtSecret = "testSecretKey";
	
	private final int jwtExpirationMs = 3600000;
	
	/*@BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.jwtSecret = jwtSecret;
        jwtUtils.jwtExpirationMs = jwtExpirationMs;
    }
	
	/*@Test
    void testGenerateJwtToken() {
		UserDetailsImpl userDetails = new UserDetailsImpl();
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        assertEquals("testUser", username);
    }*/
	
	/*@Test
    void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("testUser", username);
    }*/

}
