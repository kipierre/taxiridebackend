package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.RefreshToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	@Override
	Optional<Users> findById(Long id);

	Optional<Users> findFirstByOrderByName();

	Users findByFirstName(String firstName);

	Boolean existsByEmail(String email);

	Boolean existsByPhoneNumber(String phoneNumber);

	Boolean existsByReferalCodeAndPhoneNumber(String referalCode, String phoneNumber);

	Optional<Users> findByEmail(String email);

	Optional<Users> findByPhoneNumber(String phone);

	Optional<RefreshToken> findRefreshTokenById(Long id);

	Optional<Users> findByRefreshToken(RefreshToken refreshToken);

	// Méthode permettant de retrouver un utilisateur suivant son adresse numéro de téléphone ou son addresse mail
	@Query("SELECT user FROM Users user WHERE user.phoneNumber =:phone OR user.email =:email")
	Optional<Users> findByUsername(@Param("phone") String phone, @Param("email") String email);

}
