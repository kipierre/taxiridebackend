package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    // Rechercher tous les contacts d'un utilisateur
    List<EmergencyContact> findAllByUser(Users user);

    // Rechercher tous les contacts d'un utilisateur
    List<EmergencyContact> findAllByUserAndContactType(Users user, Integer contactType);

}
