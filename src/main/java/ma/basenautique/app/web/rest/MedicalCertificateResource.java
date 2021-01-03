package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.MedicalCertificateService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.MedicalCertificateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ma.basenautique.app.domain.MedicalCertificate}.
 */
@RestController
@RequestMapping("/api")
public class MedicalCertificateResource {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificateResource.class);

    private static final String ENTITY_NAME = "medicalCertificate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalCertificateService medicalCertificateService;

    public MedicalCertificateResource(MedicalCertificateService medicalCertificateService) {
        this.medicalCertificateService = medicalCertificateService;
    }

    /**
     * {@code POST  /medical-certificates} : Create a new medicalCertificate.
     *
     * @param medicalCertificateDTO the medicalCertificateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalCertificateDTO, or with status {@code 400 (Bad Request)} if the medicalCertificate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-certificates")
    public ResponseEntity<MedicalCertificateDTO> createMedicalCertificate(@Valid @RequestBody MedicalCertificateDTO medicalCertificateDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalCertificate : {}", medicalCertificateDTO);
        if (medicalCertificateDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalCertificateDTO result = medicalCertificateService.save(medicalCertificateDTO);
        return ResponseEntity.created(new URI("/api/medical-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-certificates} : Updates an existing medicalCertificate.
     *
     * @param medicalCertificateDTO the medicalCertificateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalCertificateDTO,
     * or with status {@code 400 (Bad Request)} if the medicalCertificateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalCertificateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-certificates")
    public ResponseEntity<MedicalCertificateDTO> updateMedicalCertificate(@Valid @RequestBody MedicalCertificateDTO medicalCertificateDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalCertificate : {}", medicalCertificateDTO);
        if (medicalCertificateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalCertificateDTO result = medicalCertificateService.save(medicalCertificateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalCertificateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-certificates} : get all the medicalCertificates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalCertificates in body.
     */
    @GetMapping("/medical-certificates")
    public ResponseEntity<List<MedicalCertificateDTO>> getAllMedicalCertificates(Pageable pageable) {
        log.debug("REST request to get a page of MedicalCertificates");
        Page<MedicalCertificateDTO> page = medicalCertificateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-certificates/:id} : get the "id" medicalCertificate.
     *
     * @param id the id of the medicalCertificateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalCertificateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-certificates/{id}")
    public ResponseEntity<MedicalCertificateDTO> getMedicalCertificate(@PathVariable Long id) {
        log.debug("REST request to get MedicalCertificate : {}", id);
        Optional<MedicalCertificateDTO> medicalCertificateDTO = medicalCertificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalCertificateDTO);
    }

    /**
     * {@code DELETE  /medical-certificates/:id} : delete the "id" medicalCertificate.
     *
     * @param id the id of the medicalCertificateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-certificates/{id}")
    public ResponseEntity<Void> deleteMedicalCertificate(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCertificate : {}", id);
        medicalCertificateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medical-certificates?query=:query} : search for the medicalCertificate corresponding
     * to the query.
     *
     * @param query the query of the medicalCertificate search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-certificates")
    public ResponseEntity<List<MedicalCertificateDTO>> searchMedicalCertificates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalCertificates for query {}", query);
        Page<MedicalCertificateDTO> page = medicalCertificateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
