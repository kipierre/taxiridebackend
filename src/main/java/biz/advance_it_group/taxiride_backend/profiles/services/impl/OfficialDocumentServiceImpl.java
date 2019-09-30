package biz.advance_it_group.taxiride_backend.profiles.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.profiles.entities.OfficialDocument;
import biz.advance_it_group.taxiride_backend.profiles.repositories.OfficialDocumentRepository;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.OfficialDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService {

    @Autowired
    OfficialDocumentRepository officialDocumentRepository;

    @Override
    public OfficialDocument save(OfficialDocument document) {
        return officialDocumentRepository.save(document);
    }

    @Override
    public void delete(OfficialDocument document) {
        officialDocumentRepository.delete(document);
    }

    @Override
    public Optional<OfficialDocument> findOne(String id) {
        return officialDocumentRepository.findById(Long.valueOf(id));
    }

    @Override
    public Optional<OfficialDocument> findOne(Long id) {
        return officialDocumentRepository.findById(id);
    }

    @Override
    public Optional<OfficialDocument> findByUserAndNumber(Users user, Integer number) {
        return officialDocumentRepository.findByUserAndNumber(user, number);
    }

    @Override
    public OfficialDocument update(OfficialDocument document, Long id) {
        document.setId(id);
        return save(document);
    }

    @Override
    public List<OfficialDocument> findAll() {
        return officialDocumentRepository.findAll();
    }

    @Override
    public List<OfficialDocument> findAllByUser(Users user) {
        return officialDocumentRepository.findAllByUser(user);
    }
}
