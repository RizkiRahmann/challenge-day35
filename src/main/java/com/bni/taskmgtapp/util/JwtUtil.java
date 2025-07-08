package com.bni.taskmgtapp.util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
    private static final String SECRET = "qwertyouaretheonepassword12345popoldankuparizki"; // Replace with your actual secret key
    private static final Key SIGNING_KEY = new SecretKeySpec(
        Base64.getDecoder().decode(SECRET),
        SignatureAlgorithm.HS256.getJcaName()
    );

    /**
     * Creates a JWT token for the given username.
     * The token is signed with the specified signing key.
     * @param username
     * @return
     */
    public String createToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .signWith(SIGNING_KEY)
            .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (Exception e) {
            return null; // Token is invalid
        }
    }
        
}
