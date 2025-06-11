package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class AuthModule {
    private static final Map<String, String> registeredUsers = new HashMap<>();
    private static final Map<String, String> activeSessions = new HashMap<>();

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 15* 60 * 1000;

    public static boolean registerUser(String username, String password) {
        if (registeredUsers.containsKey(username)) {
            System.out.println("AuthModule: User '" + username + "' already exists.");
            return false;
        }
        registeredUsers.put(username, password);
        System.out.println("AuthModule: User '" + username + "' registered successfully.");
        return true;
    }

    public static String login(String username, String password) {
        if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
            String token = generateJwtToken(username);
            activeSessions.put(token, username);
            System.out.println("AuthModule: User '" + username + "' logged in. Token: " + token);
            return token;
        }
        System.out.println("AuthModule: Invalid username or password for '" + username + "'.");
        return null;
    }

    public static void logout(String token) {
        String username = activeSessions.remove(token);
        if (username != null) {
            System.out.println("AuthModule: User '" + username + "' logged out.");
        } else {
            System.out.println("AuthModule: Invalid or expired token for logout.");
        }
    }

    private static String generateJwtToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }
    private static Date getExpirationDateFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isValid(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration != null && expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public static String getUsername(String authToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}