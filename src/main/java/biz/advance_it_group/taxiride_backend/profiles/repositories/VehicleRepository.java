package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Rechercher un véhicule suivant sa plaque d'immatriculation
    Optional<Vehicle> findByMatriculationNumber(String number);

    // Recherche de tous les véhicules associés à un utilisateur
    List<Vehicle> findAllByUser(Users user);
}
