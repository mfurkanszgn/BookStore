package com.BookStore.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
	@Value("${jwt.key}")
	private String SECRET;

	public String generateToken(String username) {
		Map<String, Object> claims = Map.of("username", username);
		return createToken(claims, username);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUser(token);
		Date expiration = extractExpiration(token);
		return userDetails.getUsername().equals(username) && expiration.after(new Date());
	}

	private Date extractExpiration(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		return claims.getExpiration();
	}

	public String extractUser(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 200))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();

	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
