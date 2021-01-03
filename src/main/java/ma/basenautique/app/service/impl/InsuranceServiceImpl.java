package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.InsuranceService;
import ma.basenautique.app.domain.Insurance;
import ma.basenautique.app.repository.InsuranceRepository;
import ma.basenautique.app.repository.search.InsuranceSearchRepository;
import ma.basenautique.app.service.dto.InsuranceDTO;
import ma.basenautique.app.service.mapper.InsuranceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Insurance}.
 */
@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService {

    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    private final InsuranceRepository insuranceRepository;

    private final InsuranceMapper insuranceMapper;

    private final InsuranceSearchRepository insuranceSearchRepository;

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, InsuranceMapper insuranceMapper, InsuranceSearchRepository insuranceSearchRepository) {
        this.insuranceRepository = insuranceRepository;
        this.insuranceMapper = insuranceMapper;
        this.insuranceSearchRepository = insuranceSearchRepository;
    }

    @Override
    public InsuranceDTO save(InsuranceDTO insuranceDTO) {
        log.debug("Request to save Insurance : {}", insuranceDTO);
        Insurance insurance = insuranceMapper.toEntity(insuranceDTO);
        insurance = insuranceRepository.save(insurance);
        InsuranceDTO result = insuranceMapper.toDto(insurance);
        insuranceSearchRepository.save(insurance);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insurances");
        return insuranceRepository.findAll(pageable)
            .map(insuranceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InsuranceDTO> findOne(Long id) {
        log.debug("Request to get Insurance : {}", id);
        return insuranceRepository.findById(id)
            .map(insuranceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insurance : {}", id);
        insuranceRepository.deleteById(id);
        insuranceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Insurances for query {}", query);
        return insuranceSearchRepository.search(queryStringQuery(query), pageable)
            .map(insuranceMapper::toDto);
    }
}
