package biz.advance_it_group.taxiride_backend.authentification.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.dto.RegistrationRequest;
import biz.advance_it_group.taxiride_backend.authentification.entities.RefreshToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.user.CustomUserDetails;

import java.util.Optional;

public interface UserService {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByPhoneNumber(String phone);

    Users getLoggedInUser(String email);

    Optional<Users> findById(Long Id);

    Users findByFirstName(String firstName);

    Users save(Users user);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phone);

    Boolean existsByReferalCodeAndPhone(String referalCode, String phone);

    Users createUser(RegistrationRequest registerRequest);

    void verifyRefreshAvailability(RefreshToken refreshToken);
    Optional<Users> findByRefreshToken(RefreshToken refreshToken) ;

    Optional<RefreshToken> findRefreshTokenById(Long id);

    void logoutUser(CustomUserDetails customUserDetails, Long id) ;

    Boolean hasDocument(Users user, Integer number);

}
