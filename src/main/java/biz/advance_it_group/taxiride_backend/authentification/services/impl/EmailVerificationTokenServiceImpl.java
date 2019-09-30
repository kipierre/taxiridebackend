package biz.advance_it_group.taxiride_backend.authentification.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.EmailVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.enums.TokenStatus;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.InvalidTokenRequestException;
import biz.advance_it_group.taxiride_backend.authentification.repositories.EmailVerificationTokenRepository;
import biz.advance_it_group.taxiride_backend.authentification.services.interfaces.EmailVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService{

	@Autowired
	private EmailVerificationTokenRepository emailVerificationTokenRepository;

	@Value("${app.token.email.verification.duration}")
	private Long emailVerificationTokenExpiryDuration;

	/**
	 * Create an email verification token and persist it in the database which will be
	 * verified by the user
	 */
	public void createVerificationToken(Users user, String token) {
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setToken(token);
		emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		emailVerificationToken.setUser(user);
		emailVerificationToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));

		emailVerificationTokenRepository.save(emailVerificationToken);
	}


	public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
		existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));

		return save(existingToken);
	}


	public Optional<EmailVerificationToken> findByToken(String token) {
		return emailVerificationTokenRepository.findByToken(token);
	}


	public EmailVerificationToken findByUser(Users user) {
		return emailVerificationTokenRepository.findByUser(user);
	}


	public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
		return emailVerificationTokenRepository.save(emailVerificationToken);
	}


	public String generateNewToken() {
		return UUID.randomUUID().toString();
	}


	public void verifyExpiration(EmailVerificationToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			throw new InvalidTokenRequestException("Email Verification Token", token.getToken(),
					"Expired token. Please issue a new request");
		}
	}

}
