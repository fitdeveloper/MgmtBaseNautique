package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.CongePoliceService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.CongePoliceDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.CongePolice}.
 */
@RestController
@RequestMapping("/api")
public class CongePoliceResource {

    private final Logger log = LoggerFactory.getLogger(CongePoliceResource.class);

    private static final String ENTITY_NAME = "congePolice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CongePoliceService congePoliceService;

    public CongePoliceResource(CongePoliceService congePoliceService) {
        this.congePoliceService = congePoliceService;
    }

    /**
     * {@code POST  /conge-polices} : Create a new congePolice.
     *
     * @param congePoliceDTO the congePoliceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new congePoliceDTO, or with status {@code 400 (Bad Request)} if the congePolice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conge-polices")
    public ResponseEntity<CongePoliceDTO> createCongePolice(@Valid @RequestBody CongePoliceDTO congePoliceDTO) throws URISyntaxException {
        log.debug("REST request to save CongePolice : {}", congePoliceDTO);
        if (congePoliceDTO.getId() != null) {
            throw new BadRequestAlertException("A new congePolice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CongePoliceDTO result = congePoliceService.save(congePoliceDTO);
        return ResponseEntity.created(new URI("/api/conge-polices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conge-polices} : Updates an existing congePolice.
     *
     * @param congePoliceDTO the congePoliceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated congePoliceDTO,
     * or with status {@code 400 (Bad Request)} if the congePoliceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the congePoliceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conge-polices")
    public ResponseEntity<CongePoliceDTO> updateCongePolice(@Valid @RequestBody CongePoliceDTO congePoliceDTO) throws URISyntaxException {
        log.debug("REST request to update CongePolice : {}", congePoliceDTO);
        if (congePoliceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CongePoliceDTO result = congePoliceService.save(congePoliceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, congePoliceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conge-polices} : get all the congePolices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of congePolices in body.
     */
    @GetMapping("/conge-polices")
    public ResponseEntity<List<CongePoliceDTO>> getAllCongePolices(Pageable pageable) {
        log.debug("REST request to get a page of CongePolices");
        Page<CongePoliceDTO> page = congePoliceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conge-polices/:id} : get the "id" congePolice.
     *
     * @param id the id of the congePoliceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the congePoliceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conge-polices/{id}")
    public ResponseEntity<CongePoliceDTO> getCongePolice(@PathVariable Long id) {
        log.debug("REST request to get CongePolice : {}", id);
        Optional<CongePoliceDTO> congePoliceDTO = congePoliceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(congePoliceDTO);
    }

    /**
     * {@code DELETE  /conge-polices/:id} : delete the "id" congePolice.
     *
     * @param id the id of the congePoliceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conge-polices/{id}")
    public ResponseEntity<Void> deleteCongePolice(@PathVariable Long id) {
        log.debug("REST request to delete CongePolice : {}", id);
        congePoliceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/conge-polices?query=:query} : search for the congePolice corresponding
     * to the query.
     *
     * @param query the query of the congePolice search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/conge-polices")
    public ResponseEntity<List<CongePoliceDTO>> searchCongePolices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CongePolices for query {}", query);
        Page<CongePoliceDTO> page = congePoliceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
