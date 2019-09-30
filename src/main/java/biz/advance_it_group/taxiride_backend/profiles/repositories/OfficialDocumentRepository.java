package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.OfficialDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficialDocumentRepository extends JpaRepository<OfficialDocument, Long> {

    Boolean existsByUserAndNumber(Users user, Integer nummber);

    // Récupérer toutes les pièces appartenant à un utilisateur
    List<OfficialDocument> findAllByUser(Users user);

    // Retrouver un document officiel de type précis pour un utilisateur donné
    Optional<OfficialDocument> findByUserAndNumber(Users user, Integer number);




}
