package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.PasswordResetToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<Instant> findExpiryDateByToken(String token);

	Boolean existsByToken(String token);

	Optional<Users> findUserByToken(String token);

	Optional<PasswordResetToken> findByToken(String token);
}