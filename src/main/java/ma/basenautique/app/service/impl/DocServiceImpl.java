package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.DocService;
import ma.basenautique.app.domain.Doc;
import ma.basenautique.app.repository.DocRepository;
import ma.basenautique.app.repository.search.DocSearchRepository;
import ma.basenautique.app.service.dto.DocDTO;
import ma.basenautique.app.service.mapper.DocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Doc}.
 */
@Service
@Transactional
public class DocServiceImpl implements DocService {

    private final Logger log = LoggerFactory.getLogger(DocServiceImpl.class);

    private final DocRepository docRepository;

    private final DocMapper docMapper;

    private final DocSearchRepository docSearchRepository;

    public DocServiceImpl(DocRepository docRepository, DocMapper docMapper, DocSearchRepository docSearchRepository) {
        this.docRepository = docRepository;
        this.docMapper = docMapper;
        this.docSearchRepository = docSearchRepository;
    }

    @Override
    public DocDTO save(DocDTO docDTO) {
        log.debug("Request to save Doc : {}", docDTO);
        Doc doc = docMapper.toEntity(docDTO);
        doc = docRepository.save(doc);
        DocDTO result = docMapper.toDto(doc);
        docSearchRepository.save(doc);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Docs");
        return docRepository.findAll(pageable)
            .map(docMapper::toDto);
    }



    /**
     *  Get all the docs where Insurance is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DocDTO> findAllWhereInsuranceIsNull() {
        log.debug("Request to get all docs where Insurance is null");
        return StreamSupport
            .stream(docRepository.findAll().spliterator(), false)
            .filter(doc -> doc.getInsurance() == null)
            .map(docMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the docs where CongePolice is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DocDTO> findAllWhereCongePoliceIsNull() {
        log.debug("Request to get all docs where CongePolice is null");
        return StreamSupport
            .stream(docRepository.findAll().spliterator(), false)
            .filter(doc -> doc.getCongePolice() == null)
            .map(docMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the docs where MedicalCertificate is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DocDTO> findAllWhereMedicalCertificateIsNull() {
        log.debug("Request to get all docs where MedicalCertificate is null");
        return StreamSupport
            .stream(docRepository.findAll().spliterator(), false)
            .filter(doc -> doc.getMedicalCertificate() == null)
            .map(docMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the docs where Dealership is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DocDTO> findAllWhereDealershipIsNull() {
        log.debug("Request to get all docs where Dealership is null");
        return StreamSupport
            .stream(docRepository.findAll().spliterator(), false)
            .filter(doc -> doc.getDealership() == null)
            .map(docMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocDTO> findOne(Long id) {
        log.debug("Request to get Doc : {}", id);
        return docRepository.findById(id)
            .map(docMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doc : {}", id);
        docRepository.deleteById(id);
        docSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Docs for query {}", query);
        return docSearchRepository.search(queryStringQuery(query), pageable)
            .map(docMapper::toDto);
    }
}
