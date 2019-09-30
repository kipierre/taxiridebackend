package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.profiles.entities.Vehicle;
import biz.advance_it_group.taxiride_backend.profiles.entities.VehiclePicture;
import biz.advance_it_group.taxiride_backend.profiles.repositories.VehiclePictureRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.VehiclePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiclePictureServiceImpl implements VehiclePictureService {

    @Autowired
    VehiclePictureRepository vehiclePictureRepository;


    @Override
    public VehiclePicture save(VehiclePicture picture) {
        return vehiclePictureRepository.save(picture);
    }

    @Override
    public void delete(VehiclePicture picture) {
        vehiclePictureRepository.delete(picture);
    }

    @Override
    public Optional<VehiclePicture> findOne(String id) {
        return vehiclePictureRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<VehiclePicture> findOne(Long id) {
        return vehiclePictureRepository.findById(id);
    }

    @Override
    public Optional<VehiclePicture> findByUrlImage(String url) {
        return vehiclePictureRepository.findByPictureURL(url);
    }

    @Override
    public VehiclePicture update(VehiclePicture picture, Long id) {
        picture.setId(id);
        return save(picture);
    }

    @Override
    public List<VehiclePicture> findAll() {
        return vehiclePictureRepository.findAll();
    }

    @Override
    public List<VehiclePicture> findAllByVehicle(Vehicle vehicle) {
        return vehiclePictureRepository.findAllByVehicle(vehicle);
    }
}
