package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.GuardingService;
import ma.basenautique.app.domain.Guarding;
import ma.basenautique.app.repository.GuardingRepository;
import ma.basenautique.app.repository.search.GuardingSearchRepository;
import ma.basenautique.app.service.dto.GuardingDTO;
import ma.basenautique.app.service.mapper.GuardingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Guarding}.
 */
@Service
@Transactional
public class GuardingServiceImpl implements GuardingService {

    private final Logger log = LoggerFactory.getLogger(GuardingServiceImpl.class);

    private final GuardingRepository guardingRepository;

    private final GuardingMapper guardingMapper;

    private final GuardingSearchRepository guardingSearchRepository;

    public GuardingServiceImpl(GuardingRepository guardingRepository, GuardingMapper guardingMapper, GuardingSearchRepository guardingSearchRepository) {
        this.guardingRepository = guardingRepository;
        this.guardingMapper = guardingMapper;
        this.guardingSearchRepository = guardingSearchRepository;
    }

    @Override
    public GuardingDTO save(GuardingDTO guardingDTO) {
        log.debug("Request to save Guarding : {}", guardingDTO);
        Guarding guarding = guardingMapper.toEntity(guardingDTO);
        guarding = guardingRepository.save(guarding);
        GuardingDTO result = guardingMapper.toDto(guarding);
        guardingSearchRepository.save(guarding);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuardingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Guardings");
        return guardingRepository.findAll(pageable)
            .map(guardingMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GuardingDTO> findOne(Long id) {
        log.debug("Request to get Guarding : {}", id);
        return guardingRepository.findById(id)
            .map(guardingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Guarding : {}", id);
        guardingRepository.deleteById(id);
        guardingSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuardingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Guardings for query {}", query);
        return guardingSearchRepository.search(queryStringQuery(query), pageable)
            .map(guardingMapper::toDto);
    }
}
