package biz.advance_it_group.taxiride_backend.profiles.services.interfaces;
import biz.advance_it_group.taxiride_backend.profiles.entities.Vehicle;
import biz.advance_it_group.taxiride_backend.profiles.entities.VehiclePicture;

import java.util.List;
import java.util.Optional;

public interface VehiclePictureService {

    VehiclePicture save(VehiclePicture picture);

    void delete(VehiclePicture picture);

    Optional<VehiclePicture> findOne(String id);

    Optional<VehiclePicture> findOne(Long id);

    Optional<VehiclePicture> findByUrlImage(String url);

    VehiclePicture update(VehiclePicture picture, Long id);

    List<VehiclePicture> findAll();

    List<VehiclePicture> findAllByVehicle(Vehicle vehicle);

}
