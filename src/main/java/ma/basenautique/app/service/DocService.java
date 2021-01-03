package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.DocDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.Doc}.
 */
public interface DocService {

    /**
     * Save a doc.
     *
     * @param docDTO the entity to save.
     * @return the persisted entity.
     */
    DocDTO save(DocDTO docDTO);

    /**
     * Get all the docs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocDTO> findAll(Pageable pageable);
    /**
     * Get all the DocDTO where Insurance is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DocDTO> findAllWhereInsuranceIsNull();
    /**
     * Get all the DocDTO where CongePolice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DocDTO> findAllWhereCongePoliceIsNull();
    /**
     * Get all the DocDTO where MedicalCertificate is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DocDTO> findAllWhereMedicalCertificateIsNull();
    /**
     * Get all the DocDTO where Dealership is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DocDTO> findAllWhereDealershipIsNull();


    /**
     * Get the "id" doc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocDTO> findOne(Long id);

    /**
     * Delete the "id" doc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the doc corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocDTO> search(String query, Pageable pageable);
}
