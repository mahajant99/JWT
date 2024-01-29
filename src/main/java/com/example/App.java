package com.example;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.*;
import java.util.Base64;

import javax.crypto.SecretKey;

public class App {

    public static void main(String[] args) {
        String username = "mahajant99";
        String secret = generateSecretKey();

        String jwt = generateJwt(username, secret);
        System.out.println("Generated JWT: " + jwt);

        boolean isVerified = verifyJwt(jwt, username, secret);
        System.out.println("JWT Verification Result: " + isVerified);

    }

    private static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public static String generateJwt(String username, String secret) {
        byte[] decodedSecret = Base64.getDecoder().decode(secret);
        Key secretKey = Keys.hmacShaKeyFor(decodedSecret);

        JwtBuilder builder = Jwts.builder()
                .id("1")
                .subject("Test JWT")
                .issuer(username)
                .signWith(secretKey);

        return builder.compact();
    }

    public static boolean verifyJwt(String jwt, String expectedIssuer, String secret) {
        try {
            byte[] decodedSecret = Base64.getDecoder().decode(secret);
            SecretKey secretKey = Keys.hmacShaKeyFor(decodedSecret);

            JwtParser parser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();

            Jws<Claims> claimsJws = parser.parseSignedClaims(jwt);
            Claims claims = claimsJws.getPayload();

            if (claims.getIssuer().equals(expectedIssuer)) {
                return true;
            }

        } catch (JwtException e) {
            e.printStackTrace();
        }
        return false;
    }
}
