package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.ContentDocService;
import ma.basenautique.app.domain.ContentDoc;
import ma.basenautique.app.repository.ContentDocRepository;
import ma.basenautique.app.repository.search.ContentDocSearchRepository;
import ma.basenautique.app.service.dto.ContentDocDTO;
import ma.basenautique.app.service.mapper.ContentDocMapper;
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
 * Service Implementation for managing {@link ContentDoc}.
 */
@Service
@Transactional
public class ContentDocServiceImpl implements ContentDocService {

    private final Logger log = LoggerFactory.getLogger(ContentDocServiceImpl.class);

    private final ContentDocRepository contentDocRepository;

    private final ContentDocMapper contentDocMapper;

    private final ContentDocSearchRepository contentDocSearchRepository;

    public ContentDocServiceImpl(ContentDocRepository contentDocRepository, ContentDocMapper contentDocMapper, ContentDocSearchRepository contentDocSearchRepository) {
        this.contentDocRepository = contentDocRepository;
        this.contentDocMapper = contentDocMapper;
        this.contentDocSearchRepository = contentDocSearchRepository;
    }

    @Override
    public ContentDocDTO save(ContentDocDTO contentDocDTO) {
        log.debug("Request to save ContentDoc : {}", contentDocDTO);
        ContentDoc contentDoc = contentDocMapper.toEntity(contentDocDTO);
        contentDoc = contentDocRepository.save(contentDoc);
        ContentDocDTO result = contentDocMapper.toDto(contentDoc);
        contentDocSearchRepository.save(contentDoc);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentDocs");
        return contentDocRepository.findAll(pageable)
            .map(contentDocMapper::toDto);
    }



    /**
     *  Get all the contentDocs where Doc is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ContentDocDTO> findAllWhereDocIsNull() {
        log.debug("Request to get all contentDocs where Doc is null");
        return StreamSupport
            .stream(contentDocRepository.findAll().spliterator(), false)
            .filter(contentDoc -> contentDoc.getDoc() == null)
            .map(contentDocMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContentDocDTO> findOne(Long id) {
        log.debug("Request to get ContentDoc : {}", id);
        return contentDocRepository.findById(id)
            .map(contentDocMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentDoc : {}", id);
        contentDocRepository.deleteById(id);
        contentDocSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContentDocs for query {}", query);
        return contentDocSearchRepository.search(queryStringQuery(query), pageable)
            .map(contentDocMapper::toDto);
    }
}
