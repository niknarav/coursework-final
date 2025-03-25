package com.example.servicesyncuserservice.security.jwt;

import com.example.servicesyncuserservice.security.AppUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public String generateJwtToken(AppUserDetails userDetails) {
        return generateTokenFromUsername(userDetails.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Невалидная подпись: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Невалидный токен: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Время жизни токена закончилось: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Неподдерживаемый формат токена: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Пустая строка: {}", e.getMessage());
        }
        return false;
    }

}
