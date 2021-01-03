package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.TypeDocDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.TypeDoc}.
 */
public interface TypeDocService {

    /**
     * Save a typeDoc.
     *
     * @param typeDocDTO the entity to save.
     * @return the persisted entity.
     */
    TypeDocDTO save(TypeDocDTO typeDocDTO);

    /**
     * Get all the typeDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeDocDTO> findAll(Pageable pageable);
    /**
     * Get all the TypeDocDTO where Doc is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TypeDocDTO> findAllWhereDocIsNull();


    /**
     * Get the "id" typeDoc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeDocDTO> findOne(Long id);

    /**
     * Delete the "id" typeDoc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the typeDoc corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeDocDTO> search(String query, Pageable pageable);
}
