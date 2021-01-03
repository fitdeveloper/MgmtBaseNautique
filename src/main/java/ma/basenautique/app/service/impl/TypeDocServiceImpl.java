package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.TypeDocService;
import ma.basenautique.app.domain.TypeDoc;
import ma.basenautique.app.repository.TypeDocRepository;
import ma.basenautique.app.repository.search.TypeDocSearchRepository;
import ma.basenautique.app.service.dto.TypeDocDTO;
import ma.basenautique.app.service.mapper.TypeDocMapper;
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
 * Service Implementation for managing {@link TypeDoc}.
 */
@Service
@Transactional
public class TypeDocServiceImpl implements TypeDocService {

    private final Logger log = LoggerFactory.getLogger(TypeDocServiceImpl.class);

    private final TypeDocRepository typeDocRepository;

    private final TypeDocMapper typeDocMapper;

    private final TypeDocSearchRepository typeDocSearchRepository;

    public TypeDocServiceImpl(TypeDocRepository typeDocRepository, TypeDocMapper typeDocMapper, TypeDocSearchRepository typeDocSearchRepository) {
        this.typeDocRepository = typeDocRepository;
        this.typeDocMapper = typeDocMapper;
        this.typeDocSearchRepository = typeDocSearchRepository;
    }

    @Override
    public TypeDocDTO save(TypeDocDTO typeDocDTO) {
        log.debug("Request to save TypeDoc : {}", typeDocDTO);
        TypeDoc typeDoc = typeDocMapper.toEntity(typeDocDTO);
        typeDoc = typeDocRepository.save(typeDoc);
        TypeDocDTO result = typeDocMapper.toDto(typeDoc);
        typeDocSearchRepository.save(typeDoc);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeDocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDocs");
        return typeDocRepository.findAll(pageable)
            .map(typeDocMapper::toDto);
    }



    /**
     *  Get all the typeDocs where Doc is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TypeDocDTO> findAllWhereDocIsNull() {
        log.debug("Request to get all typeDocs where Doc is null");
        return StreamSupport
            .stream(typeDocRepository.findAll().spliterator(), false)
            .filter(typeDoc -> typeDoc.getDoc() == null)
            .map(typeDocMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDocDTO> findOne(Long id) {
        log.debug("Request to get TypeDoc : {}", id);
        return typeDocRepository.findById(id)
            .map(typeDocMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeDoc : {}", id);
        typeDocRepository.deleteById(id);
        typeDocSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeDocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TypeDocs for query {}", query);
        return typeDocSearchRepository.search(queryStringQuery(query), pageable)
            .map(typeDocMapper::toDto);
    }
}
