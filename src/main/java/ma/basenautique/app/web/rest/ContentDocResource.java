package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.ContentDocService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.ContentDocDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.ContentDoc}.
 */
@RestController
@RequestMapping("/api")
public class ContentDocResource {

    private final Logger log = LoggerFactory.getLogger(ContentDocResource.class);

    private static final String ENTITY_NAME = "contentDoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentDocService contentDocService;

    public ContentDocResource(ContentDocService contentDocService) {
        this.contentDocService = contentDocService;
    }

    /**
     * {@code POST  /content-docs} : Create a new contentDoc.
     *
     * @param contentDocDTO the contentDocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentDocDTO, or with status {@code 400 (Bad Request)} if the contentDoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-docs")
    public ResponseEntity<ContentDocDTO> createContentDoc(@Valid @RequestBody ContentDocDTO contentDocDTO) throws URISyntaxException {
        log.debug("REST request to save ContentDoc : {}", contentDocDTO);
        if (contentDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentDocDTO result = contentDocService.save(contentDocDTO);
        return ResponseEntity.created(new URI("/api/content-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-docs} : Updates an existing contentDoc.
     *
     * @param contentDocDTO the contentDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentDocDTO,
     * or with status {@code 400 (Bad Request)} if the contentDocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-docs")
    public ResponseEntity<ContentDocDTO> updateContentDoc(@Valid @RequestBody ContentDocDTO contentDocDTO) throws URISyntaxException {
        log.debug("REST request to update ContentDoc : {}", contentDocDTO);
        if (contentDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentDocDTO result = contentDocService.save(contentDocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-docs} : get all the contentDocs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentDocs in body.
     */
    @GetMapping("/content-docs")
    public ResponseEntity<List<ContentDocDTO>> getAllContentDocs(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("doc-is-null".equals(filter)) {
            log.debug("REST request to get all ContentDocs where doc is null");
            return new ResponseEntity<>(contentDocService.findAllWhereDocIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ContentDocs");
        Page<ContentDocDTO> page = contentDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-docs/:id} : get the "id" contentDoc.
     *
     * @param id the id of the contentDocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentDocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-docs/{id}")
    public ResponseEntity<ContentDocDTO> getContentDoc(@PathVariable Long id) {
        log.debug("REST request to get ContentDoc : {}", id);
        Optional<ContentDocDTO> contentDocDTO = contentDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentDocDTO);
    }

    /**
     * {@code DELETE  /content-docs/:id} : delete the "id" contentDoc.
     *
     * @param id the id of the contentDocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-docs/{id}")
    public ResponseEntity<Void> deleteContentDoc(@PathVariable Long id) {
        log.debug("REST request to delete ContentDoc : {}", id);
        contentDocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/content-docs?query=:query} : search for the contentDoc corresponding
     * to the query.
     *
     * @param query the query of the contentDoc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/content-docs")
    public ResponseEntity<List<ContentDocDTO>> searchContentDocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContentDocs for query {}", query);
        Page<ContentDocDTO> page = contentDocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
