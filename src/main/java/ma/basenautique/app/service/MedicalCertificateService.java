package ma.basenautique.app.service;

import ma.basenautique.app.service.dto.MedicalCertificateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ma.basenautique.app.domain.MedicalCertificate}.
 */
public interface MedicalCertificateService {

    /**
     * Save a medicalCertificate.
     *
     * @param medicalCertificateDTO the entity to save.
     * @return the persisted entity.
     */
    MedicalCertificateDTO save(MedicalCertificateDTO medicalCertificateDTO);

    /**
     * Get all the medicalCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCertificateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicalCertificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalCertificateDTO> findOne(Long id);

    /**
     * Delete the "id" medicalCertificate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalCertificate corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCertificateDTO> search(String query, Pageable pageable);
}
