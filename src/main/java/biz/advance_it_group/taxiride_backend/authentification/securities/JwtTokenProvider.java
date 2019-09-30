package biz.advance_it_group.taxiride_backend.authentification.securities;

import biz.advance_it_group.taxiride_backend.authentification.configurations.AppProperties;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.InvalidTokenRequestException;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.user.CustomUserDetails;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private static final Logger logger = Logger.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration}")
	private Long jwtExpirationInMs;

	@Value("${app.jwt.claims.refresh.name}")
	private String jwtClaimRefreshName;
	public JwtTokenProvider() {
	}
	private AppProperties appProperties;

	public JwtTokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * Génère un jeton à partir d'un objet principal. Incorporer le jeton d'actualisation dans le jwt
	 * afin qu'un nouveau JWT puisse être créé
	 */
	public String generateToken(CustomUserDetails customUserDetails) {
		Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
		return Jwts.builder()
				.setSubject(customUserDetails.getUsername())
				.setSubject(Long.toString(customUserDetails.getId()))
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * * Génère un jeton à partir d'un objet principal. Incorporer le jeton d'actualisation dans le jwt
	 * afin qu'un nouveau JWT puisse être créé
	 */
	public String generateTokenFromUserId(Long userId) {
		Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
		return Jwts.builder()
				.setSubject(Long.toString(userId))
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * Renvoie l'ID utilisateur encapsulé dans le jeton.
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		return Long.parseLong(claims.getSubject());
	}

	public Long getUserIdFromTokene(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(appProperties.getAuth().getTokenSecret())
				.parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}


	/**
	 * Valide si un jeton a la signature non malformée correcte et s'il n'a pas expiré ou n'est pas pris en charge.
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required.");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
		}
	}

	/**
	 * Renvoie l'expiration JWT pour le client afin qu'il puisse s'exécuter
	 * la logique de jeton d'actualisation appropriée
	 */
	public Long getExpiryDuration() {
		return jwtExpirationInMs;
	}

	public String generateToken(Authentication authentication) {

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
		return Jwts.builder()
				.setSubject(customUserDetails.getUsername())

				.setSubject(Long.toString(customUserDetails.getId()))
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

	}

	public String createTokenn(CustomUserDetails customUserDetails) {

		Instant expiryDate = Instant.now().plusMillis(appProperties.getAuth().getTokenExpirationMsec());


		return Jwts.builder()
				.setSubject(Long.toString(customUserDetails.getId()))
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate))
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
				.compact();
	}

	public String createToken(Authentication authentication) {

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				.setSubject(Long.toString(customUserDetails.getId()))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

}