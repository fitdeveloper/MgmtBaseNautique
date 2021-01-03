package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.GuardingService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.GuardingDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.Guarding}.
 */
@RestController
@RequestMapping("/api")
public class GuardingResource {

    private final Logger log = LoggerFactory.getLogger(GuardingResource.class);

    private static final String ENTITY_NAME = "guarding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuardingService guardingService;

    public GuardingResource(GuardingService guardingService) {
        this.guardingService = guardingService;
    }

    /**
     * {@code POST  /guardings} : Create a new guarding.
     *
     * @param guardingDTO the guardingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guardingDTO, or with status {@code 400 (Bad Request)} if the guarding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guardings")
    public ResponseEntity<GuardingDTO> createGuarding(@Valid @RequestBody GuardingDTO guardingDTO) throws URISyntaxException {
        log.debug("REST request to save Guarding : {}", guardingDTO);
        if (guardingDTO.getId() != null) {
            throw new BadRequestAlertException("A new guarding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuardingDTO result = guardingService.save(guardingDTO);
        return ResponseEntity.created(new URI("/api/guardings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guardings} : Updates an existing guarding.
     *
     * @param guardingDTO the guardingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guardingDTO,
     * or with status {@code 400 (Bad Request)} if the guardingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guardingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guardings")
    public ResponseEntity<GuardingDTO> updateGuarding(@Valid @RequestBody GuardingDTO guardingDTO) throws URISyntaxException {
        log.debug("REST request to update Guarding : {}", guardingDTO);
        if (guardingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GuardingDTO result = guardingService.save(guardingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guardingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /guardings} : get all the guardings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guardings in body.
     */
    @GetMapping("/guardings")
    public ResponseEntity<List<GuardingDTO>> getAllGuardings(Pageable pageable) {
        log.debug("REST request to get a page of Guardings");
        Page<GuardingDTO> page = guardingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guardings/:id} : get the "id" guarding.
     *
     * @param id the id of the guardingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guardingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guardings/{id}")
    public ResponseEntity<GuardingDTO> getGuarding(@PathVariable Long id) {
        log.debug("REST request to get Guarding : {}", id);
        Optional<GuardingDTO> guardingDTO = guardingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guardingDTO);
    }

    /**
     * {@code DELETE  /guardings/:id} : delete the "id" guarding.
     *
     * @param id the id of the guardingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guardings/{id}")
    public ResponseEntity<Void> deleteGuarding(@PathVariable Long id) {
        log.debug("REST request to delete Guarding : {}", id);
        guardingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/guardings?query=:query} : search for the guarding corresponding
     * to the query.
     *
     * @param query the query of the guarding search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/guardings")
    public ResponseEntity<List<GuardingDTO>> searchGuardings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Guardings for query {}", query);
        Page<GuardingDTO> page = guardingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
