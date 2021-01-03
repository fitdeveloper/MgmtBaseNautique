package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.MembershipDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Membership}.
 */
public interface MembershipService {

    /**
     * Save a membership.
     *
     * @param membershipDTO the entity to save.
     * @return the persisted entity.
     */
    MembershipDTO save(MembershipDTO membershipDTO);

    /**
     * Get all the memberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipDTO> findAll(Pageable pageable);


    /**
     * Get the "id" membership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembershipDTO> findOne(Long id);

    /**
     * Delete the "id" membership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the membership corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipDTO> search(String query, Pageable pageable);
}
