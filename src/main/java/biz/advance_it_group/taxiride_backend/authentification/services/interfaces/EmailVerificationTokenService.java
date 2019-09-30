package biz.advance_it_group.taxiride_backend.authentification.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.entities.EmailVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;

import java.util.Optional;

public interface EmailVerificationTokenService {

     void createVerificationToken(Users user, String token) ;

     EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) ;

     Optional<EmailVerificationToken> findByToken(String token) ;
     EmailVerificationToken findByUser(Users user);

     EmailVerificationToken save(EmailVerificationToken emailVerificationToken) ;

     String generateNewToken();

     void verifyExpiration(EmailVerificationToken token) ;
}
