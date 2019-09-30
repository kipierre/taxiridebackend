package biz.advance_it_group.taxiride_backend.profiles.services.interfaces;


import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.UserPaymentMode;

import java.util.List;
import java.util.Optional;

public interface UserPaymentModeService {

    UserPaymentMode save(UserPaymentMode paymentMode);

    void delete(UserPaymentMode paymentMode);

    Optional<UserPaymentMode> findOne(String id);

    Optional<UserPaymentMode> findOne(Long id);

    UserPaymentMode update(UserPaymentMode paymentMode, Long id);

    List<UserPaymentMode> findAll();

    List<UserPaymentMode> findAllByUser(Users user);

}
