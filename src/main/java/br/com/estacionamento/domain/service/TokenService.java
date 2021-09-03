package br.com.estacionamento.domain.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	private static long expirationTime = (100 * 60 * 100);
	private SecretKey secretKey  = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//	private SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	
	
	public String generateToken(EstacionamentoDomainModel estacionamento) {
		return Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject(estacionamento.getNome())
				.setExpiration(new Date((System.currentTimeMillis() + expirationTime)))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}


	public Claims decodeTokem(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	
}
