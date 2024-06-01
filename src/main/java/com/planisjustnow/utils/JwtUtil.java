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
        String cookieValue = "token=" + token + "; Path=/; HttpOnly; SameSite=None; Secure";
        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
//        httpServletResponse.addCookie(cookie);
        httpServletResponse.addHeader("Set-Cookie",cookieValue);
    }

    public String parseToken(HttpServletRequest request, HttpServletResponse httpServletResponse){
        try{
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

                String userId = claimsJws.getBody().getSubject();
                System.out.println(userId);
                return userId;
            }
        }catch (ExpiredJwtException e){
            String userId = e.getClaims().getSubject();
            createToken(userId,httpServletResponse);
            System.out.println("토큰 유효 기간 지남. 새로 발급함.");
            return userId;
        }catch (NullPointerException e){
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
    public void logout(HttpServletResponse httpServletResponse){
        String cookieValue = "token=; Path=/; HttpOnly; SameSite=None; Secure; Max-Age=0";
        httpServletResponse.addHeader("Set-Cookie", cookieValue);
    }

}
