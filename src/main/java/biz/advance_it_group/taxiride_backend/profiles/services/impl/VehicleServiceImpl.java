package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.Vehicle;
import biz.advance_it_group.taxiride_backend.profiles.repositories.VehicleRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;


    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Optional<Vehicle> findOne(String id) {
        return vehicleRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<Vehicle> findOne(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Optional<Vehicle> findByImmatriculationNumber(String number) {
        return vehicleRepository.findByMatriculationNumber(number);
    }

    @Override
    public Vehicle update(Vehicle vehicle, Long id) {
        vehicle.setId(id);
        return save(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> findAllByUser(Users user) {
        return vehicleRepository.findAllByUser(user);
    }
}
