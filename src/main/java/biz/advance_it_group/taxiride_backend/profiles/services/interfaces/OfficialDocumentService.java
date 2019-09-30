package biz.advance_it_group.taxiride_backend.profiles.services.interfaces;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.OfficialDocument;

import java.util.List;
import java.util.Optional;

public interface OfficialDocumentService {
    
    OfficialDocument save(OfficialDocument document);

    void delete(OfficialDocument document);

    Optional<OfficialDocument> findOne(String id);

    Optional<OfficialDocument> findOne(Long id);

    Optional<OfficialDocument> findByUserAndNumber(Users user, Integer number);

    OfficialDocument update(OfficialDocument document, Long id);

    List<OfficialDocument> findAll();

    List<OfficialDocument> findAllByUser(Users user);

}
