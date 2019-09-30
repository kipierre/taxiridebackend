package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.UserPaymentMode;
import biz.advance_it_group.taxiride_backend.profiles.repositories.UserPaymentModeRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.UserPaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPaymentModeServiceImpl implements UserPaymentModeService {

    @Autowired
    UserPaymentModeRepository userPaymentModeRepository;


    @Override
    public UserPaymentMode save(UserPaymentMode paymentMode) {
        return userPaymentModeRepository.save(paymentMode);
    }

    @Override
    public void delete(UserPaymentMode paymentMode) {
        userPaymentModeRepository.delete(paymentMode);
    }

    @Override
    public Optional<UserPaymentMode> findOne(String id) {
        return userPaymentModeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<UserPaymentMode> findOne(Long id) {
        return userPaymentModeRepository.findById(id);
    }

    @Override
    public UserPaymentMode update(UserPaymentMode paymentMode, Long id) {
        paymentMode.setId(id);
        return save(paymentMode);
    }

    @Override
    public List<UserPaymentMode> findAll() {
        return userPaymentModeRepository.findAll();
    }

    @Override
    public List<UserPaymentMode> findAllByUser(Users user) {
        return userPaymentModeRepository.findAllByUser(user);
    }
}
