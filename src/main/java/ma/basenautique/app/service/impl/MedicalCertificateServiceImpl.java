package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.MedicalCertificateService;
import ma.basenautique.app.domain.MedicalCertificate;
import ma.basenautique.app.repository.MedicalCertificateRepository;
import ma.basenautique.app.repository.search.MedicalCertificateSearchRepository;
import ma.basenautique.app.service.dto.MedicalCertificateDTO;
import ma.basenautique.app.service.mapper.MedicalCertificateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MedicalCertificate}.
 */
@Service
@Transactional
public class MedicalCertificateServiceImpl implements MedicalCertificateService {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificateServiceImpl.class);

    private final MedicalCertificateRepository medicalCertificateRepository;

    private final MedicalCertificateMapper medicalCertificateMapper;

    private final MedicalCertificateSearchRepository medicalCertificateSearchRepository;

    public MedicalCertificateServiceImpl(MedicalCertificateRepository medicalCertificateRepository, MedicalCertificateMapper medicalCertificateMapper, MedicalCertificateSearchRepository medicalCertificateSearchRepository) {
        this.medicalCertificateRepository = medicalCertificateRepository;
        this.medicalCertificateMapper = medicalCertificateMapper;
        this.medicalCertificateSearchRepository = medicalCertificateSearchRepository;
    }

    @Override
    public MedicalCertificateDTO save(MedicalCertificateDTO medicalCertificateDTO) {
        log.debug("Request to save MedicalCertificate : {}", medicalCertificateDTO);
        MedicalCertificate medicalCertificate = medicalCertificateMapper.toEntity(medicalCertificateDTO);
        medicalCertificate = medicalCertificateRepository.save(medicalCertificate);
        MedicalCertificateDTO result = medicalCertificateMapper.toDto(medicalCertificate);
        medicalCertificateSearchRepository.save(medicalCertificate);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCertificateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalCertificates");
        return medicalCertificateRepository.findAll(pageable)
            .map(medicalCertificateMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalCertificateDTO> findOne(Long id) {
        log.debug("Request to get MedicalCertificate : {}", id);
        return medicalCertificateRepository.findById(id)
            .map(medicalCertificateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalCertificate : {}", id);
        medicalCertificateRepository.deleteById(id);
        medicalCertificateSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCertificateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicalCertificates for query {}", query);
        return medicalCertificateSearchRepository.search(queryStringQuery(query), pageable)
            .map(medicalCertificateMapper::toDto);
    }
}
