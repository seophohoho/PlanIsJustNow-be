package com.planisjustnow.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key jwtSecretKey;
    private final long jwtExpiry;

    public JwtUtil(@Value("${jwt.key}") String jwtSecretKey, @Value("${jwt.expiry}") long jwtExpiry) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        this.jwtExpiry = jwtExpiry;
    }

    public void createToken(String userId, HttpServletResponse httpServletResponse) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiry))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);  // Secure 속성을 설정하지 않음
        httpServletResponse.addCookie(cookie);
    }

    public String parseToken(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        try {
            Cookie[] cookies = request.getCookies();
            String token = Arrays.stream(cookies)
                    .filter(c -> "token".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            if (token != null) {
                JwtParser parser = Jwts.parserBuilder().setSigningKey(jwtSecretKey).build();
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);

                String userId = claimsJws.getBody().getSubject();
                System.out.println(userId);
                return userId;
            }
        } catch (ExpiredJwtException e) {
            String userId = e.getClaims().getSubject();
            createToken(userId, httpServletResponse);
            System.out.println("토큰 유효 기간 지남. 새로 발급함.");
            return userId;
        } catch (NullPointerException e) {
            return "fail:Token-not-found";
        }
        return null;
    }

    public void logout(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(false);  // Secure 속성을 설정하지 않음
        httpServletResponse.addCookie(cookie);
    }
}
