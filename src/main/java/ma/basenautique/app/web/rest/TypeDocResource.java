package ma.basenautique.app.web.rest;

import ma.basenautique.app.service.TypeDocService;
import ma.basenautique.app.web.rest.errors.BadRequestAlertException;
import ma.basenautique.app.service.dto.TypeDocDTO;

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
 * REST controller for managing {@link ma.basenautique.app.domain.TypeDoc}.
 */
@RestController
@RequestMapping("/api")
public class TypeDocResource {

    private final Logger log = LoggerFactory.getLogger(TypeDocResource.class);

    private static final String ENTITY_NAME = "typeDoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeDocService typeDocService;

    public TypeDocResource(TypeDocService typeDocService) {
        this.typeDocService = typeDocService;
    }

    /**
     * {@code POST  /type-docs} : Create a new typeDoc.
     *
     * @param typeDocDTO the typeDocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDocDTO, or with status {@code 400 (Bad Request)} if the typeDoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-docs")
    public ResponseEntity<TypeDocDTO> createTypeDoc(@Valid @RequestBody TypeDocDTO typeDocDTO) throws URISyntaxException {
        log.debug("REST request to save TypeDoc : {}", typeDocDTO);
        if (typeDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDocDTO result = typeDocService.save(typeDocDTO);
        return ResponseEntity.created(new URI("/api/type-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-docs} : Updates an existing typeDoc.
     *
     * @param typeDocDTO the typeDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDocDTO,
     * or with status {@code 400 (Bad Request)} if the typeDocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-docs")
    public ResponseEntity<TypeDocDTO> updateTypeDoc(@Valid @RequestBody TypeDocDTO typeDocDTO) throws URISyntaxException {
        log.debug("REST request to update TypeDoc : {}", typeDocDTO);
        if (typeDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDocDTO result = typeDocService.save(typeDocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-docs} : get all the typeDocs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeDocs in body.
     */
    @GetMapping("/type-docs")
    public ResponseEntity<List<TypeDocDTO>> getAllTypeDocs(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("doc-is-null".equals(filter)) {
            log.debug("REST request to get all TypeDocs where doc is null");
            return new ResponseEntity<>(typeDocService.findAllWhereDocIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TypeDocs");
        Page<TypeDocDTO> page = typeDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-docs/:id} : get the "id" typeDoc.
     *
     * @param id the id of the typeDocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-docs/{id}")
    public ResponseEntity<TypeDocDTO> getTypeDoc(@PathVariable Long id) {
        log.debug("REST request to get TypeDoc : {}", id);
        Optional<TypeDocDTO> typeDocDTO = typeDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDocDTO);
    }

    /**
     * {@code DELETE  /type-docs/:id} : delete the "id" typeDoc.
     *
     * @param id the id of the typeDocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-docs/{id}")
    public ResponseEntity<Void> deleteTypeDoc(@PathVariable Long id) {
        log.debug("REST request to delete TypeDoc : {}", id);
        typeDocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/type-docs?query=:query} : search for the typeDoc corresponding
     * to the query.
     *
     * @param query the query of the typeDoc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/type-docs")
    public ResponseEntity<List<TypeDocDTO>> searchTypeDocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeDocs for query {}", query);
        Page<TypeDocDTO> page = typeDocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
