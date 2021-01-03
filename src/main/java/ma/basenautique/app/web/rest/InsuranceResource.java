package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.InsuranceService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.InsuranceDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.Insurance}.
 */
@RestController
@RequestMapping("/api")
public class InsuranceResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceResource.class);

    private static final String ENTITY_NAME = "insurance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsuranceService insuranceService;

    public InsuranceResource(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    /**
     * {@code POST  /insurances} : Create a new insurance.
     *
     * @param insuranceDTO the insuranceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insuranceDTO, or with status {@code 400 (Bad Request)} if the insurance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insurances")
    public ResponseEntity<InsuranceDTO> createInsurance(@Valid @RequestBody InsuranceDTO insuranceDTO) throws URISyntaxException {
        log.debug("REST request to save Insurance : {}", insuranceDTO);
        if (insuranceDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsuranceDTO result = insuranceService.save(insuranceDTO);
        return ResponseEntity.created(new URI("/api/insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insurances} : Updates an existing insurance.
     *
     * @param insuranceDTO the insuranceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insuranceDTO,
     * or with status {@code 400 (Bad Request)} if the insuranceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insuranceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insurances")
    public ResponseEntity<InsuranceDTO> updateInsurance(@Valid @RequestBody InsuranceDTO insuranceDTO) throws URISyntaxException {
        log.debug("REST request to update Insurance : {}", insuranceDTO);
        if (insuranceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsuranceDTO result = insuranceService.save(insuranceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insuranceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insurances} : get all the insurances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insurances in body.
     */
    @GetMapping("/insurances")
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances(Pageable pageable) {
        log.debug("REST request to get a page of Insurances");
        Page<InsuranceDTO> page = insuranceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insurances/:id} : get the "id" insurance.
     *
     * @param id the id of the insuranceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insuranceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insurances/{id}")
    public ResponseEntity<InsuranceDTO> getInsurance(@PathVariable Long id) {
        log.debug("REST request to get Insurance : {}", id);
        Optional<InsuranceDTO> insuranceDTO = insuranceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insuranceDTO);
    }

    /**
     * {@code DELETE  /insurances/:id} : delete the "id" insurance.
     *
     * @param id the id of the insuranceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insurances/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        log.debug("REST request to delete Insurance : {}", id);
        insuranceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/insurances?query=:query} : search for the insurance corresponding
     * to the query.
     *
     * @param query the query of the insurance search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/insurances")
    public ResponseEntity<List<InsuranceDTO>> searchInsurances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Insurances for query {}", query);
        Page<InsuranceDTO> page = insuranceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
