package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.MemberDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Member}.
 */
public interface MemberService {

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save.
     * @return the persisted entity.
     */
    MemberDTO save(MemberDTO memberDTO);

    /**
     * Get all the members.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MemberDTO> findAll(Pageable pageable);
    /**
     * Get all the MemberDTO where Child is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MemberDTO> findAllWhereChildIsNull();


    /**
     * Get the "id" member.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MemberDTO> findOne(Long id);

    /**
     * Delete the "id" member.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the member corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MemberDTO> search(String query, Pageable pageable);
}
