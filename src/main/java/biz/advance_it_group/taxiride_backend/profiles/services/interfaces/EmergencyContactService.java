package biz.advance_it_group.taxiride_backend.profiles.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.EmergencyContact;

import java.util.List;
import java.util.Optional;

public interface EmergencyContactService {

    EmergencyContact save(EmergencyContact contact);

    void delete(EmergencyContact contact);

    Optional<EmergencyContact> findOne(String id);

    Optional<EmergencyContact> findOne(Long id);

    EmergencyContact update(EmergencyContact contact, Long id);

    List<EmergencyContact> findAll();

    List<EmergencyContact> findAllByUser(Users user);

    List<EmergencyContact> findAllByUserAndContactType(Users user, Integer type);
}
