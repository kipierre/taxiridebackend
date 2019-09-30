package biz.advance_it_group.taxiride_backend.authentification.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.RefreshToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.TokenRefreshException;
import biz.advance_it_group.taxiride_backend.authentification.repositories.RefreshTokenRepository;
import biz.advance_it_group.taxiride_backend.authentification.services.interfaces.RefreshTokenService;
import biz.advance_it_group.taxiride_backend.commons.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Value("${app.token.refresh.duration}")
	private Long refreshTokenDurationMs;

	public Optional<RefreshToken> findById(Long id) {
		return refreshTokenRepository.findById(id);
	}

	public Optional<String> findTokenById(Long id) {
		return refreshTokenRepository.findTokenById(id);
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public Optional<Users> findUserById(Long id) {
		return refreshTokenRepository.findUserById(id);
	}

	public Optional<Users> findUserByToken(String token) {
		return refreshTokenRepository.findUserByToken(token);
	}

	/**
	 * Conserver l'instance refreshToken mise à jour dans la base de données
	 */
	public RefreshToken save(RefreshToken refreshToken) {
		return refreshTokenRepository.save(refreshToken);
	}


	/**
	 * * Crée et retourne un nouveau jeton d'actualisation
	 */
	public RefreshToken createRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(Util.generateRandomUuid());
		refreshToken.setRefreshCount(0L);

		refreshToken.getUser();
		return refreshToken;
	}


	/**
	 * Verify whether the token provided has expired or not on the basis of the current
	 * server time and/or throw error otherwise
	 * Vérifier si le jeton fourni a expiré ou non sur la base du courant
	 * heure du serveur et / ou erreur de projection sinon
	 */
	public void verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
		}
	}

	/**
	 * Delete the refresh token associated with the user device
	 * Supprimer le jeton d'actualisation associé à la machine utilisateur
	 */
	public void deleteById(Long id) {
		refreshTokenRepository.deleteById(id);
	}

	/**
	 * Increase the count of the token usage in the database. Useful for
	 * audit purposes
	 * Augmentez le nombre de jetons utilisés dans la base de données. Utile pour
	 * fins d'audit
	 */
	public void increaseCount(RefreshToken refreshToken) {
		refreshToken.incrementRefreshCount();
		save(refreshToken);
	}
}
