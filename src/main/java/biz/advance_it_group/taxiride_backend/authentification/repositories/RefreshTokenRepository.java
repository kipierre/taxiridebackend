package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.RefreshToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<String> findTokenById(Long id);

	Optional<RefreshToken> findByToken(String token);

	Optional<Users> findUserById(Long id);

	Optional<Users> findUserByToken(String token);
}
