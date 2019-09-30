package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.PhoneVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneVerificationTokenRepository extends JpaRepository<PhoneVerificationToken, Long> {
    Optional<PhoneVerificationToken> findByToken(String token);
    PhoneVerificationToken findByUser(Users user);
}
