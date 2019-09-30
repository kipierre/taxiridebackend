package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.EmailVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	Optional<EmailVerificationToken> findByToken(String token);
	EmailVerificationToken findByUser(Users user);
}
