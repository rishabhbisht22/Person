package com.person.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MalformedKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${person.app.jwtSecret}")
    private String jwtSecret;

    @Value("${person.app.jwtExpirationMS}")
    private long expirationTime;

    public String generateToken(Authentication authentication) {
        PersonDetailsImpl personPrincipal = (PersonDetailsImpl) authentication.getPrincipal();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expirationTime);

        String token = Jwts.builder()
                .subject(personPrincipal.getUsername())
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
            return true;
        }
        catch(MalformedJwtException exception) {
            log.debug("Jwt token is invalid :",exception.getMessage());
        }
        catch (ExpiredJwtException exception) {
            log.debug("Jwt Token is expired :",exception.getMessage());
        }
        catch (UnsupportedJwtException exception) {
            log.debug("Jwt Token is unsupported :",exception.getMessage());
        }
        catch(IllegalArgumentException exception) {
            log.debug("Jwt claim string is empty :",exception.getMessage());
        }
        return false;
    }
}
