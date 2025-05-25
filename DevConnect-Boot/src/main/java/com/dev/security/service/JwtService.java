/*
 * package com.dev.security.service;
 * 
 * import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service;
 * 
 * @Service public class JwtService {
 * 
 * @Value("${jwt.secret}") private String secretKey;
 * 
 * public String extractUsername(String token) {
 * System.out.println("JwtService.extractUsername() - Token received: " +
 * token); String username =
 * Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).
 * getBody() .getSubject();
 * System.out.println("JwtService.extractUsername() - Username extracted: " +
 * username); return username; }
 * 
 * }
 */