package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.CongePoliceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.CongePolice}.
 */
public interface CongePoliceService {

    /**
     * Save a congePolice.
     *
     * @param congePoliceDTO the entity to save.
     * @return the persisted entity.
     */
    CongePoliceDTO save(CongePoliceDTO congePoliceDTO);

    /**
     * Get all the congePolices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CongePoliceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" congePolice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CongePoliceDTO> findOne(Long id);

    /**
     * Delete the "id" congePolice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the congePolice corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CongePoliceDTO> search(String query, Pageable pageable);
}
