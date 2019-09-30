package biz.advance_it_group.taxiride_backend.authentification.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {

    PasswordResetToken save(PasswordResetToken passwordResetToken) ;

    Optional<PasswordResetToken> findByToken(String token) ;

    PasswordResetToken createToken();

    void verifyExpiration(PasswordResetToken token);
}
