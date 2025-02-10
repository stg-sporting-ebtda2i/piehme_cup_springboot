package com.stgsporting.piehmecup.services;


import com.stgsporting.piehmecup.dtos.AuthInfo;
import com.stgsporting.piehmecup.entities.Details;
import com.stgsporting.piehmecup.entities.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.days}")
    private Long EXPIRATION_DAYS;

    public String generateUserToken(AuthInfo info) {
        return generateToken(info, false);
    }

    public String generateAdminToken(AuthInfo info) {
        return generateToken(info, true);
    }

    public String generateToken(AuthInfo info, boolean isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(String.valueOf(info.getUserId()))
                .add("is_admin", isAdmin)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60 * 60 * EXPIRATION_DAYS))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        byte[] secretBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public boolean isTokenValid(String token, Details userDetail) {
        Long userId = userDetail.getId();
        return userId.equals(extractUserId(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public boolean isAdmin(String token) {
        return extractClaim(token, claims -> claims.get("is_admin", Boolean.class));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public void setEXPIRATION_DAYS(Long EXPIRATION_DAYS) {
        this.EXPIRATION_DAYS = EXPIRATION_DAYS;
    }

    public Long getRemainingExpirationTime(String token) {
        Date expirationDate = extractExpiration(token);
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expirationDate.getTime();

        if (expirationTimeMillis > currentTimeMillis) {
            return expirationTimeMillis - currentTimeMillis;
        } else {
            return 0L; // Token has already expired
        }
    }

}
