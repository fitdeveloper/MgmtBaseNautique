package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.DocService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.DocDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.Doc}.
 */
@RestController
@RequestMapping("/api")
public class DocResource {

    private final Logger log = LoggerFactory.getLogger(DocResource.class);

    private static final String ENTITY_NAME = "doc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocService docService;

    public DocResource(DocService docService) {
        this.docService = docService;
    }

    /**
     * {@code POST  /docs} : Create a new doc.
     *
     * @param docDTO the docDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docDTO, or with status {@code 400 (Bad Request)} if the doc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/docs")
    public ResponseEntity<DocDTO> createDoc(@Valid @RequestBody DocDTO docDTO) throws URISyntaxException {
        log.debug("REST request to save Doc : {}", docDTO);
        if (docDTO.getId() != null) {
            throw new BadRequestAlertException("A new doc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocDTO result = docService.save(docDTO);
        return ResponseEntity.created(new URI("/api/docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /docs} : Updates an existing doc.
     *
     * @param docDTO the docDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docDTO,
     * or with status {@code 400 (Bad Request)} if the docDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/docs")
    public ResponseEntity<DocDTO> updateDoc(@Valid @RequestBody DocDTO docDTO) throws URISyntaxException {
        log.debug("REST request to update Doc : {}", docDTO);
        if (docDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocDTO result = docService.save(docDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /docs} : get all the docs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docs in body.
     */
    @GetMapping("/docs")
    public ResponseEntity<List<DocDTO>> getAllDocs(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("insurance-is-null".equals(filter)) {
            log.debug("REST request to get all Docs where insurance is null");
            return new ResponseEntity<>(docService.findAllWhereInsuranceIsNull(),
                    HttpStatus.OK);
        }
        if ("congepolice-is-null".equals(filter)) {
            log.debug("REST request to get all Docs where congePolice is null");
            return new ResponseEntity<>(docService.findAllWhereCongePoliceIsNull(),
                    HttpStatus.OK);
        }
        if ("medicalcertificate-is-null".equals(filter)) {
            log.debug("REST request to get all Docs where medicalCertificate is null");
            return new ResponseEntity<>(docService.findAllWhereMedicalCertificateIsNull(),
                    HttpStatus.OK);
        }
        if ("dealership-is-null".equals(filter)) {
            log.debug("REST request to get all Docs where dealership is null");
            return new ResponseEntity<>(docService.findAllWhereDealershipIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Docs");
        Page<DocDTO> page = docService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /docs/:id} : get the "id" doc.
     *
     * @param id the id of the docDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/docs/{id}")
    public ResponseEntity<DocDTO> getDoc(@PathVariable Long id) {
        log.debug("REST request to get Doc : {}", id);
        Optional<DocDTO> docDTO = docService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docDTO);
    }

    /**
     * {@code DELETE  /docs/:id} : delete the "id" doc.
     *
     * @param id the id of the docDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/docs/{id}")
    public ResponseEntity<Void> deleteDoc(@PathVariable Long id) {
        log.debug("REST request to delete Doc : {}", id);
        docService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/docs?query=:query} : search for the doc corresponding
     * to the query.
     *
     * @param query the query of the doc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/docs")
    public ResponseEntity<List<DocDTO>> searchDocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Docs for query {}", query);
        Page<DocDTO> page = docService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
