package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.DealershipDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Dealership}.
 */
public interface DealershipService {

    /**
     * Save a dealership.
     *
     * @param dealershipDTO the entity to save.
     * @return the persisted entity.
     */
    DealershipDTO save(DealershipDTO dealershipDTO);

    /**
     * Get all the dealerships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DealershipDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dealership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DealershipDTO> findOne(Long id);

    /**
     * Delete the "id" dealership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dealership corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DealershipDTO> search(String query, Pageable pageable);
}
