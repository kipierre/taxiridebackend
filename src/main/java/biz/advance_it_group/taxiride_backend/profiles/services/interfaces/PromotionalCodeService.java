package biz.advance_it_group.taxiride_backend.profiles.services.interfaces;

import biz.advance_it_group.taxiride_backend.profiles.entities.PromotionalCode;

import java.util.List;
import java.util.Optional;

public interface PromotionalCodeService {

    PromotionalCode save(PromotionalCode code);

    void delete(PromotionalCode code);

    Optional<PromotionalCode> findOne(String id);

    Optional<PromotionalCode> findOne(Long id);

    // Vérifier que le code existe avec à partir de la clé
    Boolean existsByCodeKey(String key);

    // Rechercher le code promotionnel suivant la clé
    Optional<PromotionalCode> findByKey(String key);

    PromotionalCode update(PromotionalCode code, Long id);

    List<PromotionalCode> findAll();

}
