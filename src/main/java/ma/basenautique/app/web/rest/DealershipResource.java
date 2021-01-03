package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.DealershipService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.DealershipDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.Dealership}.
 */
@RestController
@RequestMapping("/api")
public class DealershipResource {

    private final Logger log = LoggerFactory.getLogger(DealershipResource.class);

    private static final String ENTITY_NAME = "dealership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealershipService dealershipService;

    public DealershipResource(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    /**
     * {@code POST  /dealerships} : Create a new dealership.
     *
     * @param dealershipDTO the dealershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealershipDTO, or with status {@code 400 (Bad Request)} if the dealership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dealerships")
    public ResponseEntity<DealershipDTO> createDealership(@Valid @RequestBody DealershipDTO dealershipDTO) throws URISyntaxException {
        log.debug("REST request to save Dealership : {}", dealershipDTO);
        if (dealershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new dealership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealershipDTO result = dealershipService.save(dealershipDTO);
        return ResponseEntity.created(new URI("/api/dealerships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dealerships} : Updates an existing dealership.
     *
     * @param dealershipDTO the dealershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealershipDTO,
     * or with status {@code 400 (Bad Request)} if the dealershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dealerships")
    public ResponseEntity<DealershipDTO> updateDealership(@Valid @RequestBody DealershipDTO dealershipDTO) throws URISyntaxException {
        log.debug("REST request to update Dealership : {}", dealershipDTO);
        if (dealershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DealershipDTO result = dealershipService.save(dealershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dealershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dealerships} : get all the dealerships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealerships in body.
     */
    @GetMapping("/dealerships")
    public ResponseEntity<List<DealershipDTO>> getAllDealerships(Pageable pageable) {
        log.debug("REST request to get a page of Dealerships");
        Page<DealershipDTO> page = dealershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dealerships/:id} : get the "id" dealership.
     *
     * @param id the id of the dealershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dealerships/{id}")
    public ResponseEntity<DealershipDTO> getDealership(@PathVariable Long id) {
        log.debug("REST request to get Dealership : {}", id);
        Optional<DealershipDTO> dealershipDTO = dealershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealershipDTO);
    }

    /**
     * {@code DELETE  /dealerships/:id} : delete the "id" dealership.
     *
     * @param id the id of the dealershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dealerships/{id}")
    public ResponseEntity<Void> deleteDealership(@PathVariable Long id) {
        log.debug("REST request to delete Dealership : {}", id);
        dealershipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dealerships?query=:query} : search for the dealership corresponding
     * to the query.
     *
     * @param query the query of the dealership search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dealerships")
    public ResponseEntity<List<DealershipDTO>> searchDealerships(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dealerships for query {}", query);
        Page<DealershipDTO> page = dealershipService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
