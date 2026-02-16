package com.example.Ecomerce.common.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServices {
    private static final String SECRECT_KEY = "5bc18830-657d-410c-9a29-86330e3e8cb25bc18830-657d-410c-9a29-86330e3e8cb2";
    private static final long REFRESG_WINDOW = 1000 + 60 * 60 * 24 * 7;
    private static final long TOKEN_EXPIRATION = 1000;

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = Map.of("authorities", userDetails
                .getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority)
                .toList());
        return generateToken(claims, userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().
                setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() * TOKEN_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64URL.decode(SECRECT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("Error al validar JWT", e);

            throw new RuntimeException("Invalid JWT token or mal forwed", e);

        }


    }

    private <T> T getEspecificName(String token, Function<Claims, T> claimsMapper) {

        Claims allClaims = getAllClaims(token);
        return claimsMapper.apply(allClaims);
    }

    public String getUsername(String token) {
        return getEspecificName(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getEspecificName(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public boolean canBeTokenRenew(String token) {
        return getExpirationDate(token).after(new Date(System.currentTimeMillis() + REFRESG_WINDOW));
    }

    public String renewToken(UserDetails userDetails, String token) {
        if (!canBeTokenRenew(token)) {
            throw new RuntimeException("Token cannot be renewed");
        }
        return generateToken(userDetails);

    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername());
    }
}
