package com.carsale.auth.services.utils;

import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	@Value("${jwt.secret:aGVsbG9Xb3JsZDMyYnl0ZXNlY3JldGtleWZvcmNhcnNhbGVhcHAxMjM=}")
	private String secret;

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolvers) {
		return claimResolvers.apply(extractAllClaims(token));
	}

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		return extractClaims(token, claims -> claims.get("role", String.class));
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private String generateToken(Map<String, Object> extractClaims, UserDetails userDetails,String role) {
		return Jwts.builder().setClaims(extractClaims)
				.claim("role", role)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateToken(UserDetails userDetails, String role) {
        return generateToken(new HashMap<>(), userDetails, role);
    }

    public String generateToken(UserDetails userDetails) {
        if (userDetails.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("User role is required for token generation.");
        }
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        return generateToken(userDetails, role);
    }

}
