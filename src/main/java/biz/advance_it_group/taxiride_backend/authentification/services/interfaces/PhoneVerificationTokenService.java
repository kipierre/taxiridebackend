package biz.advance_it_group.taxiride_backend.authentification.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.entities.PhoneVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;

import java.util.Optional;

public interface PhoneVerificationTokenService {

    void createVerificationToken(Users user, String token) ;

    PhoneVerificationToken updateExistingTokenWithNameAndExpiry(PhoneVerificationToken existingToken) ;

    Optional<PhoneVerificationToken> findByToken(String token) ;
    PhoneVerificationToken findByUser(Users user);

    PhoneVerificationToken save(PhoneVerificationToken emailVerificationToken) ;

    String generateNewToken();

    void verifyExpiration(PhoneVerificationToken token) ;

}
