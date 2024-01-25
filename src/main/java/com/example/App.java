package com.example;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.*;
import java.util.Base64;

public class App {

    public static void main(String[] args) {
        String username = "mahajant99";
        String secret = generateSecretKey();

        String jwt = generateJwt(username, secret);
        System.out.println("Generated JWT: " + jwt);

    }

    private static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public static String generateJwt(String username, String secret) {

        Key secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));

        JwtBuilder builder = Jwts.builder()
                .id("1")
                .subject("Test JWT")
                .issuer(username)
                .signWith(secretKey);

        return builder.compact();
    }
}
