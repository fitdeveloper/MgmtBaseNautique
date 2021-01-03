package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.AssociationService;
import ma.basenautique.app.domain.Association;
import ma.basenautique.app.repository.AssociationRepository;
import ma.basenautique.app.repository.search.AssociationSearchRepository;
import ma.basenautique.app.service.dto.AssociationDTO;
import ma.basenautique.app.service.mapper.AssociationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationServiceImpl implements AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationServiceImpl.class);

    private final AssociationRepository associationRepository;

    private final AssociationMapper associationMapper;

    private final AssociationSearchRepository associationSearchRepository;

    public AssociationServiceImpl(AssociationRepository associationRepository, AssociationMapper associationMapper, AssociationSearchRepository associationSearchRepository) {
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
        this.associationSearchRepository = associationSearchRepository;
    }

    @Override
    public AssociationDTO save(AssociationDTO associationDTO) {
        log.debug("Request to save Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        AssociationDTO result = associationMapper.toDto(association);
        associationSearchRepository.save(association);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssociationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Associations");
        return associationRepository.findAll(pageable)
            .map(associationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AssociationDTO> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id)
            .map(associationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
        associationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssociationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Associations for query {}", query);
        return associationSearchRepository.search(queryStringQuery(query), pageable)
            .map(associationMapper::toDto);
    }
}
