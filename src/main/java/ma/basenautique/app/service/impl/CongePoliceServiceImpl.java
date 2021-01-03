package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.CongePoliceService;
import ma.basenautique.app.domain.CongePolice;
import ma.basenautique.app.repository.CongePoliceRepository;
import ma.basenautique.app.repository.search.CongePoliceSearchRepository;
import ma.basenautique.app.service.dto.CongePoliceDTO;
import ma.basenautique.app.service.mapper.CongePoliceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CongePolice}.
 */
@Service
@Transactional
public class CongePoliceServiceImpl implements CongePoliceService {

    private final Logger log = LoggerFactory.getLogger(CongePoliceServiceImpl.class);

    private final CongePoliceRepository congePoliceRepository;

    private final CongePoliceMapper congePoliceMapper;

    private final CongePoliceSearchRepository congePoliceSearchRepository;

    public CongePoliceServiceImpl(CongePoliceRepository congePoliceRepository, CongePoliceMapper congePoliceMapper, CongePoliceSearchRepository congePoliceSearchRepository) {
        this.congePoliceRepository = congePoliceRepository;
        this.congePoliceMapper = congePoliceMapper;
        this.congePoliceSearchRepository = congePoliceSearchRepository;
    }

    @Override
    public CongePoliceDTO save(CongePoliceDTO congePoliceDTO) {
        log.debug("Request to save CongePolice : {}", congePoliceDTO);
        CongePolice congePolice = congePoliceMapper.toEntity(congePoliceDTO);
        congePolice = congePoliceRepository.save(congePolice);
        CongePoliceDTO result = congePoliceMapper.toDto(congePolice);
        congePoliceSearchRepository.save(congePolice);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CongePoliceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CongePolices");
        return congePoliceRepository.findAll(pageable)
            .map(congePoliceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CongePoliceDTO> findOne(Long id) {
        log.debug("Request to get CongePolice : {}", id);
        return congePoliceRepository.findById(id)
            .map(congePoliceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CongePolice : {}", id);
        congePoliceRepository.deleteById(id);
        congePoliceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CongePoliceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CongePolices for query {}", query);
        return congePoliceSearchRepository.search(queryStringQuery(query), pageable)
            .map(congePoliceMapper::toDto);
    }
}
