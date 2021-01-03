package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.DealershipService;
import ma.basenautique.app.domain.Dealership;
import ma.basenautique.app.repository.DealershipRepository;
import ma.basenautique.app.repository.search.DealershipSearchRepository;
import ma.basenautique.app.service.dto.DealershipDTO;
import ma.basenautique.app.service.mapper.DealershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Dealership}.
 */
@Service
@Transactional
public class DealershipServiceImpl implements DealershipService {

    private final Logger log = LoggerFactory.getLogger(DealershipServiceImpl.class);

    private final DealershipRepository dealershipRepository;

    private final DealershipMapper dealershipMapper;

    private final DealershipSearchRepository dealershipSearchRepository;

    public DealershipServiceImpl(DealershipRepository dealershipRepository, DealershipMapper dealershipMapper, DealershipSearchRepository dealershipSearchRepository) {
        this.dealershipRepository = dealershipRepository;
        this.dealershipMapper = dealershipMapper;
        this.dealershipSearchRepository = dealershipSearchRepository;
    }

    @Override
    public DealershipDTO save(DealershipDTO dealershipDTO) {
        log.debug("Request to save Dealership : {}", dealershipDTO);
        Dealership dealership = dealershipMapper.toEntity(dealershipDTO);
        dealership = dealershipRepository.save(dealership);
        DealershipDTO result = dealershipMapper.toDto(dealership);
        dealershipSearchRepository.save(dealership);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DealershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dealerships");
        return dealershipRepository.findAll(pageable)
            .map(dealershipMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DealershipDTO> findOne(Long id) {
        log.debug("Request to get Dealership : {}", id);
        return dealershipRepository.findById(id)
            .map(dealershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dealership : {}", id);
        dealershipRepository.deleteById(id);
        dealershipSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DealershipDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dealerships for query {}", query);
        return dealershipSearchRepository.search(queryStringQuery(query), pageable)
            .map(dealershipMapper::toDto);
    }
}
