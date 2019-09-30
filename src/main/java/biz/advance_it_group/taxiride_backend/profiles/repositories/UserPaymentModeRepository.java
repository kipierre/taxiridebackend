package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.UserPaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPaymentModeRepository extends JpaRepository<UserPaymentMode, Long> {
    // Rechercher tous les modes de paiement d'un utilisateur
    List<UserPaymentMode> findAllByUser(Users user);

}
