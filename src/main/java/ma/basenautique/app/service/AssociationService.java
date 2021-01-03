package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.AssociationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Association}.
 */
public interface AssociationService {

    /**
     * Save a association.
     *
     * @param associationDTO the entity to save.
     * @return the persisted entity.
     */
    AssociationDTO save(AssociationDTO associationDTO);

    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssociationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" association.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssociationDTO> findOne(Long id);

    /**
     * Delete the "id" association.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the association corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssociationDTO> search(String query, Pageable pageable);
}
