package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.InsuranceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Insurance}.
 */
public interface InsuranceService {

    /**
     * Save a insurance.
     *
     * @param insuranceDTO the entity to save.
     * @return the persisted entity.
     */
    InsuranceDTO save(InsuranceDTO insuranceDTO);

    /**
     * Get all the insurances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InsuranceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" insurance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InsuranceDTO> findOne(Long id);

    /**
     * Delete the "id" insurance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the insurance corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InsuranceDTO> search(String query, Pageable pageable);
}
