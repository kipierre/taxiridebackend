package biz.advance_it_group.taxiride_backend.profiles.repositories;

import biz.advance_it_group.taxiride_backend.profiles.entities.Vehicle;
import biz.advance_it_group.taxiride_backend.profiles.entities.VehiclePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiclePictureRepository extends JpaRepository<VehiclePicture, Long> {

    // Retrouver une image de véhicule à partir de son url
    Optional<VehiclePicture> findByPictureURL(String url);

    // Rechercher toutes les images associées à un véhicule
    List<VehiclePicture> findAllByVehicle(Vehicle vehicle);

}
