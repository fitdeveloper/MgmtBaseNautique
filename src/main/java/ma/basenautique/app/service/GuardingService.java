package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.GuardingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Guarding}.
 */
public interface GuardingService {

    /**
     * Save a guarding.
     *
     * @param guardingDTO the entity to save.
     * @return the persisted entity.
     */
    GuardingDTO save(GuardingDTO guardingDTO);

    /**
     * Get all the guardings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GuardingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" guarding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GuardingDTO> findOne(Long id);

    /**
     * Delete the "id" guarding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the guarding corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GuardingDTO> search(String query, Pageable pageable);
}
