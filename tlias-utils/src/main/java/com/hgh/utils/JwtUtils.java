package com.hgh.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET = "muxinhgh2929450662";

    private static final long EXPIRE = 12 * 60 * 60 * 1000L;

    private static final SecretKey KEY;

    static {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(SECRET.getBytes(StandardCharsets.UTF_8));
            String base64Key = Base64.getEncoder().encodeToString(hash);
            KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
        } catch (Exception e) {
            throw new RuntimeException("JWT 密钥初始化失败", e);
        }
    }

    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(KEY)
                .compact();
    }

    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}