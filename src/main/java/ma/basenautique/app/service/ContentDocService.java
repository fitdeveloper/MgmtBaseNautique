package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.ContentDocDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.ContentDoc}.
 */
public interface ContentDocService {

    /**
     * Save a contentDoc.
     *
     * @param contentDocDTO the entity to save.
     * @return the persisted entity.
     */
    ContentDocDTO save(ContentDocDTO contentDocDTO);

    /**
     * Get all the contentDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentDocDTO> findAll(Pageable pageable);
    /**
     * Get all the ContentDocDTO where Doc is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ContentDocDTO> findAllWhereDocIsNull();


    /**
     * Get the "id" contentDoc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContentDocDTO> findOne(Long id);

    /**
     * Delete the "id" contentDoc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the contentDoc corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentDocDTO> search(String query, Pageable pageable);
}
