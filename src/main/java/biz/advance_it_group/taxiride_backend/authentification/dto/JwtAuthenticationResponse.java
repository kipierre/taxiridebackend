package biz.advance_it_group.taxiride_backend.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

	private String accessToken;

	private String refreshToken;

	private String tokenType;

	private Long expiryDuration;

	private String username; // Informations supplémentaires lors de la connexion de l'utilisateur

	private Collection<? extends GrantedAuthority> authorities;

	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";
	}

	// Constructeur qui permet de retourner les informations supplémentaires de l'utilisateur connecté (en plus du username et des rôles)
	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration, String username, Collection<? extends GrantedAuthority> authorities) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";

		// informations supplémentaires lors de la connexion d'un utilisateur
		this.username = username;
		this.authorities = authorities;
	}

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	public JwtAuthenticationResponse(String accessToken, Long expiryDuration) {
		this.accessToken = accessToken;
	}

}
