package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.TypeDoc;
import ma.basenautique.app.repository.TypeDocRepository;
import ma.basenautique.app.repository.search.TypeDocSearchRepository;
import ma.basenautique.app.service.TypeDocService;
import ma.basenautique.app.service.dto.TypeDocDTO;
import ma.basenautique.app.service.mapper.TypeDocMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TypeDocResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeDocResourceIT {

    private static final String DEFAULT_NUMBER_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_TYPE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_TYPE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_DESC_TYPE_DOC = "BBBBBBBBBB";

    @Autowired
    private TypeDocRepository typeDocRepository;

    @Autowired
    private TypeDocMapper typeDocMapper;

    @Autowired
    private TypeDocService typeDocService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.TypeDocSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeDocSearchRepository mockTypeDocSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeDocMockMvc;

    private TypeDoc typeDoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeDoc createEntity(EntityManager em) {
        TypeDoc typeDoc = new TypeDoc()
            .numberTypeDoc(DEFAULT_NUMBER_TYPE_DOC)
            .titleTypeDoc(DEFAULT_TITLE_TYPE_DOC)
            .descTypeDoc(DEFAULT_DESC_TYPE_DOC);
        return typeDoc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeDoc createUpdatedEntity(EntityManager em) {
        TypeDoc typeDoc = new TypeDoc()
            .numberTypeDoc(UPDATED_NUMBER_TYPE_DOC)
            .titleTypeDoc(UPDATED_TITLE_TYPE_DOC)
            .descTypeDoc(UPDATED_DESC_TYPE_DOC);
        return typeDoc;
    }

    @BeforeEach
    public void initTest() {
        typeDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDoc() throws Exception {
        int databaseSizeBeforeCreate = typeDocRepository.findAll().size();
        // Create the TypeDoc
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(typeDoc);
        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDoc testTypeDoc = typeDocList.get(typeDocList.size() - 1);
        assertThat(testTypeDoc.getNumberTypeDoc()).isEqualTo(DEFAULT_NUMBER_TYPE_DOC);
        assertThat(testTypeDoc.getTitleTypeDoc()).isEqualTo(DEFAULT_TITLE_TYPE_DOC);
        assertThat(testTypeDoc.getDescTypeDoc()).isEqualTo(DEFAULT_DESC_TYPE_DOC);

        // Validate the TypeDoc in Elasticsearch
        verify(mockTypeDocSearchRepository, times(1)).save(testTypeDoc);
    }

    @Test
    @Transactional
    public void createTypeDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDocRepository.findAll().size();

        // Create the TypeDoc with an existing ID
        typeDoc.setId(1L);
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(typeDoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeDoc in Elasticsearch
        verify(mockTypeDocSearchRepository, times(0)).save(typeDoc);
    }


    @Test
    @Transactional
    public void checkNumberTypeDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeDocRepository.findAll().size();
        // set the field null
        typeDoc.setNumberTypeDoc(null);

        // Create the TypeDoc, which fails.
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(typeDoc);


        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isBadRequest());

        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleTypeDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeDocRepository.findAll().size();
        // set the field null
        typeDoc.setTitleTypeDoc(null);

        // Create the TypeDoc, which fails.
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(typeDoc);


        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isBadRequest());

        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeDocs() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        // Get all the typeDocList
        restTypeDocMockMvc.perform(get("/api/type-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberTypeDoc").value(hasItem(DEFAULT_NUMBER_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].titleTypeDoc").value(hasItem(DEFAULT_TITLE_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].descTypeDoc").value(hasItem(DEFAULT_DESC_TYPE_DOC)));
    }
    
    @Test
    @Transactional
    public void getTypeDoc() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        // Get the typeDoc
        restTypeDocMockMvc.perform(get("/api/type-docs/{id}", typeDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeDoc.getId().intValue()))
            .andExpect(jsonPath("$.numberTypeDoc").value(DEFAULT_NUMBER_TYPE_DOC))
            .andExpect(jsonPath("$.titleTypeDoc").value(DEFAULT_TITLE_TYPE_DOC))
            .andExpect(jsonPath("$.descTypeDoc").value(DEFAULT_DESC_TYPE_DOC));
    }
    @Test
    @Transactional
    public void getNonExistingTypeDoc() throws Exception {
        // Get the typeDoc
        restTypeDocMockMvc.perform(get("/api/type-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDoc() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        int databaseSizeBeforeUpdate = typeDocRepository.findAll().size();

        // Update the typeDoc
        TypeDoc updatedTypeDoc = typeDocRepository.findById(typeDoc.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDoc are not directly saved in db
        em.detach(updatedTypeDoc);
        updatedTypeDoc
            .numberTypeDoc(UPDATED_NUMBER_TYPE_DOC)
            .titleTypeDoc(UPDATED_TITLE_TYPE_DOC)
            .descTypeDoc(UPDATED_DESC_TYPE_DOC);
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(updatedTypeDoc);

        restTypeDocMockMvc.perform(put("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isOk());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeUpdate);
        TypeDoc testTypeDoc = typeDocList.get(typeDocList.size() - 1);
        assertThat(testTypeDoc.getNumberTypeDoc()).isEqualTo(UPDATED_NUMBER_TYPE_DOC);
        assertThat(testTypeDoc.getTitleTypeDoc()).isEqualTo(UPDATED_TITLE_TYPE_DOC);
        assertThat(testTypeDoc.getDescTypeDoc()).isEqualTo(UPDATED_DESC_TYPE_DOC);

        // Validate the TypeDoc in Elasticsearch
        verify(mockTypeDocSearchRepository, times(1)).save(testTypeDoc);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDoc() throws Exception {
        int databaseSizeBeforeUpdate = typeDocRepository.findAll().size();

        // Create the TypeDoc
        TypeDocDTO typeDocDTO = typeDocMapper.toDto(typeDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDocMockMvc.perform(put("/api/type-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeDoc in Elasticsearch
        verify(mockTypeDocSearchRepository, times(0)).save(typeDoc);
    }

    @Test
    @Transactional
    public void deleteTypeDoc() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        int databaseSizeBeforeDelete = typeDocRepository.findAll().size();

        // Delete the typeDoc
        restTypeDocMockMvc.perform(delete("/api/type-docs/{id}", typeDoc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeDoc in Elasticsearch
        verify(mockTypeDocSearchRepository, times(1)).deleteById(typeDoc.getId());
    }

    @Test
    @Transactional
    public void searchTypeDoc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);
        when(mockTypeDocSearchRepository.search(queryStringQuery("id:" + typeDoc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typeDoc), PageRequest.of(0, 1), 1));

        // Search the typeDoc
        restTypeDocMockMvc.perform(get("/api/_search/type-docs?query=id:" + typeDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberTypeDoc").value(hasItem(DEFAULT_NUMBER_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].titleTypeDoc").value(hasItem(DEFAULT_TITLE_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].descTypeDoc").value(hasItem(DEFAULT_DESC_TYPE_DOC)));
    }
}
