package com.planisjustnow.utils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtil {
    private String jwtSecretKey;
    private long jwtExpiry;

    public JwtUtil(@Value("${jwt.key}") String jwtSecretKey,@Value("${jwt.expiry}") long jwtExpiry){
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpiry = jwtExpiry;
    }

    public void createToken(String userId, HttpServletResponse httpServletResponse){
        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiry))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public void parseToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = Arrays.stream(cookies)
                .filter(c -> "token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token != null) {
            byte[] key = jwtSecretKey.getBytes();

            // 새로운 API로 변경된 부분
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

            Jws<Claims> claimsJws = parser.parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            String userId = claims.getSubject();
            System.out.println(userId);
        } else {
            System.out.println("Token not found in cookies");
        }
    }
    public static String generateHS256SecretKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keyLength];
        secureRandom.nextBytes(key);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
    }

}
