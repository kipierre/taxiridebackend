package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.profiles.entities.PaymentMode;
import biz.advance_it_group.taxiride_backend.profiles.repositories.PaymentModeRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.PaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentModeServiceImpl implements PaymentModeService {

    @Autowired
    private PaymentModeRepository paymentModeRepository;

    @Override
    public PaymentMode save(PaymentMode payment) {
        return paymentModeRepository.save(payment);
    }

    @Override
    public void delete(PaymentMode payment) {
        paymentModeRepository.delete(payment);
    }

    @Override
    public Optional<PaymentMode> findOne(String id) {
        return paymentModeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<PaymentMode> findOne(Long id) {
        return paymentModeRepository.findById(id);
    }

    @Override
    public Optional<PaymentMode> findByPaymentType(Integer type) {
        return paymentModeRepository.findByPaymentType(type);
    }

    @Override
    public PaymentMode update(PaymentMode payment, Long id) {
        payment.setId(id);
        return save(payment);
    }

    @Override
    public List<PaymentMode> findAll() {
        return paymentModeRepository.findAll();
    }
}
