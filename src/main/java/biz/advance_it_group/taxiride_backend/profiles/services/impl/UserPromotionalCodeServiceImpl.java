package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.PromotionalCode;
import biz.advance_it_group.taxiride_backend.profiles.entities.UserPromotionalCode;
import biz.advance_it_group.taxiride_backend.profiles.repositories.PromotionalCodeRepository;
import biz.advance_it_group.taxiride_backend.profiles.repositories.UserPromotionalCodeRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.UserPromotionalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPromotionalCodeServiceImpl implements UserPromotionalCodeService {

    @Autowired
    UserPromotionalCodeRepository userPromotionalCodeRepository;

    @Autowired
    PromotionalCodeRepository promotionalCodeRepository;

    @Override
    public UserPromotionalCode save(UserPromotionalCode code) {
        return userPromotionalCodeRepository.save(code);
    }

    @Override
    public void delete(UserPromotionalCode code) {
        userPromotionalCodeRepository.delete(code);
    }

    @Override
    public Optional<UserPromotionalCode> findOne(String id) {
        return userPromotionalCodeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<UserPromotionalCode> findOne(Long id) {
        return userPromotionalCodeRepository.findById(id);
    }


    @Override
    public UserPromotionalCode update(UserPromotionalCode code, Long id) {
        code.setId(id);
        return save(code);
    }

    @Override
    public Boolean existsByUserAndPromotionalCode(Users user, PromotionalCode promotionalCode) {
        return userPromotionalCodeRepository.existsByUserAndPromotionalCode(user, promotionalCode);
    }

    @Override
    public Boolean existByKey(String key) {
        // Retrouver d'abord le code promotionnel
        Optional<PromotionalCode> code = promotionalCodeRepository.findByCodeKey(key);
        if(code.isPresent()){
            return existByPromotionalCode(code.get());
        }else{
            return false;
        }
    }

    @Override
    public Boolean existByPromotionalCode(PromotionalCode code) {
        return userPromotionalCodeRepository.existsByPromotionalCode(code);
    }

    @Override
    public List<UserPromotionalCode> findAll() {
        return userPromotionalCodeRepository.findAll();
    }

    @Override
    public List<UserPromotionalCode> findAllByUser(Users user) {
        return userPromotionalCodeRepository.findAllByUser(user);
    }
}
