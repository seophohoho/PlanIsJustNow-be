package com.planisjustnow.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final String jwtSecretKey;
    private final long jwtExpiry;
    private final boolean isSecure;

    public JwtUtil(@Value("${jwt.key}") String jwtSecretKey, @Value("${jwt.expiry}") long jwtExpiry, @Value("${jwt.secure}") boolean isSecure) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpiry = jwtExpiry;
        this.isSecure = isSecure;
    }

    public void createToken(String userId, HttpServletResponse httpServletResponse) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiry))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        if (isSecure) {
            cookie.setSecure(true);
            httpServletResponse.addHeader("Set-Cookie", createSameSiteCookieValue(cookie, "None"));
        } else {
            cookie.setSecure(false);
            httpServletResponse.addCookie(cookie);
        }
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
                byte[] key = jwtSecretKey.getBytes();

                JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
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

    public static String generateHS256SecretKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keyLength];
        secureRandom.nextBytes(key);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
    }

    public void logout(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        if (isSecure) {
            cookie.setSecure(true);
            httpServletResponse.addHeader("Set-Cookie", createSameSiteCookieValue(cookie, "None"));
        } else {
            cookie.setSecure(false);
            httpServletResponse.addCookie(cookie);
        }
    }

    private String createSameSiteCookieValue(Cookie cookie, String sameSite) {
        StringBuilder builder = new StringBuilder();
        builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        builder.append(" Path=").append(cookie.getPath()).append(";");
        if (cookie.getSecure()) {
            builder.append(" Secure;");
        }
        if (cookie.isHttpOnly()) {
            builder.append(" HttpOnly;");
        }
        builder.append(" SameSite=").append(sameSite).append(";");
        if (cookie.getMaxAge() > 0) {
            builder.append(" Max-Age=").append(cookie.getMaxAge()).append(";");
        }
        return builder.toString();
    }
}
