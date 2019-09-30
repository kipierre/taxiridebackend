package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.PromotionalCode;
import biz.advance_it_group.taxiride_backend.profiles.entities.UserPromotionalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPromotionalCodeRepository extends JpaRepository<UserPromotionalCode, Long> {

    // Rechercher tous les codes promotionnels d'un utilisateur
    List<UserPromotionalCode> findAllByUser(Users user);

    // Vérifie si un code promotionnel existe par utilisateur et par clé
    Boolean existsByUserAndPromotionalCode(Users user, PromotionalCode promotionalCode);

    // Vérifie si un code promotionnel de l'utilisateur existe
    Boolean existsByPromotionalCode(PromotionalCode code);

}
