package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.ContentDoc;
import ma.basenautique.app.repository.ContentDocRepository;
import ma.basenautique.app.repository.search.ContentDocSearchRepository;
import ma.basenautique.app.service.ContentDocService;
import ma.basenautique.app.service.dto.ContentDocDTO;
import ma.basenautique.app.service.mapper.ContentDocMapper;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ContentDocResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContentDocResourceIT {

    private static final String DEFAULT_NUMBER_CONTENT_DOC = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_CONTENT_DOC = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DATA_CONTENT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_CONTENT_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_DOC_CONTENT_TYPE = "image/png";

    @Autowired
    private ContentDocRepository contentDocRepository;

    @Autowired
    private ContentDocMapper contentDocMapper;

    @Autowired
    private ContentDocService contentDocService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.ContentDocSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContentDocSearchRepository mockContentDocSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentDocMockMvc;

    private ContentDoc contentDoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentDoc createEntity(EntityManager em) {
        ContentDoc contentDoc = new ContentDoc()
            .numberContentDoc(DEFAULT_NUMBER_CONTENT_DOC)
            .dataContentDoc(DEFAULT_DATA_CONTENT_DOC)
            .dataContentDocContentType(DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE);
        return contentDoc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentDoc createUpdatedEntity(EntityManager em) {
        ContentDoc contentDoc = new ContentDoc()
            .numberContentDoc(UPDATED_NUMBER_CONTENT_DOC)
            .dataContentDoc(UPDATED_DATA_CONTENT_DOC)
            .dataContentDocContentType(UPDATED_DATA_CONTENT_DOC_CONTENT_TYPE);
        return contentDoc;
    }

    @BeforeEach
    public void initTest() {
        contentDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentDoc() throws Exception {
        int databaseSizeBeforeCreate = contentDocRepository.findAll().size();
        // Create the ContentDoc
        ContentDocDTO contentDocDTO = contentDocMapper.toDto(contentDoc);
        restContentDocMockMvc.perform(post("/api/content-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDocDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentDoc in the database
        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeCreate + 1);
        ContentDoc testContentDoc = contentDocList.get(contentDocList.size() - 1);
        assertThat(testContentDoc.getNumberContentDoc()).isEqualTo(DEFAULT_NUMBER_CONTENT_DOC);
        assertThat(testContentDoc.getDataContentDoc()).isEqualTo(DEFAULT_DATA_CONTENT_DOC);
        assertThat(testContentDoc.getDataContentDocContentType()).isEqualTo(DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE);

        // Validate the ContentDoc in Elasticsearch
        verify(mockContentDocSearchRepository, times(1)).save(testContentDoc);
    }

    @Test
    @Transactional
    public void createContentDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentDocRepository.findAll().size();

        // Create the ContentDoc with an existing ID
        contentDoc.setId(1L);
        ContentDocDTO contentDocDTO = contentDocMapper.toDto(contentDoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentDocMockMvc.perform(post("/api/content-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentDoc in the database
        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContentDoc in Elasticsearch
        verify(mockContentDocSearchRepository, times(0)).save(contentDoc);
    }


    @Test
    @Transactional
    public void checkNumberContentDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentDocRepository.findAll().size();
        // set the field null
        contentDoc.setNumberContentDoc(null);

        // Create the ContentDoc, which fails.
        ContentDocDTO contentDocDTO = contentDocMapper.toDto(contentDoc);


        restContentDocMockMvc.perform(post("/api/content-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDocDTO)))
            .andExpect(status().isBadRequest());

        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentDocs() throws Exception {
        // Initialize the database
        contentDocRepository.saveAndFlush(contentDoc);

        // Get all the contentDocList
        restContentDocMockMvc.perform(get("/api/content-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberContentDoc").value(hasItem(DEFAULT_NUMBER_CONTENT_DOC)))
            .andExpect(jsonPath("$.[*].dataContentDocContentType").value(hasItem(DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataContentDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_CONTENT_DOC))));
    }
    
    @Test
    @Transactional
    public void getContentDoc() throws Exception {
        // Initialize the database
        contentDocRepository.saveAndFlush(contentDoc);

        // Get the contentDoc
        restContentDocMockMvc.perform(get("/api/content-docs/{id}", contentDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentDoc.getId().intValue()))
            .andExpect(jsonPath("$.numberContentDoc").value(DEFAULT_NUMBER_CONTENT_DOC))
            .andExpect(jsonPath("$.dataContentDocContentType").value(DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataContentDoc").value(Base64Utils.encodeToString(DEFAULT_DATA_CONTENT_DOC)));
    }
    @Test
    @Transactional
    public void getNonExistingContentDoc() throws Exception {
        // Get the contentDoc
        restContentDocMockMvc.perform(get("/api/content-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentDoc() throws Exception {
        // Initialize the database
        contentDocRepository.saveAndFlush(contentDoc);

        int databaseSizeBeforeUpdate = contentDocRepository.findAll().size();

        // Update the contentDoc
        ContentDoc updatedContentDoc = contentDocRepository.findById(contentDoc.getId()).get();
        // Disconnect from session so that the updates on updatedContentDoc are not directly saved in db
        em.detach(updatedContentDoc);
        updatedContentDoc
            .numberContentDoc(UPDATED_NUMBER_CONTENT_DOC)
            .dataContentDoc(UPDATED_DATA_CONTENT_DOC)
            .dataContentDocContentType(UPDATED_DATA_CONTENT_DOC_CONTENT_TYPE);
        ContentDocDTO contentDocDTO = contentDocMapper.toDto(updatedContentDoc);

        restContentDocMockMvc.perform(put("/api/content-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDocDTO)))
            .andExpect(status().isOk());

        // Validate the ContentDoc in the database
        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeUpdate);
        ContentDoc testContentDoc = contentDocList.get(contentDocList.size() - 1);
        assertThat(testContentDoc.getNumberContentDoc()).isEqualTo(UPDATED_NUMBER_CONTENT_DOC);
        assertThat(testContentDoc.getDataContentDoc()).isEqualTo(UPDATED_DATA_CONTENT_DOC);
        assertThat(testContentDoc.getDataContentDocContentType()).isEqualTo(UPDATED_DATA_CONTENT_DOC_CONTENT_TYPE);

        // Validate the ContentDoc in Elasticsearch
        verify(mockContentDocSearchRepository, times(1)).save(testContentDoc);
    }

    @Test
    @Transactional
    public void updateNonExistingContentDoc() throws Exception {
        int databaseSizeBeforeUpdate = contentDocRepository.findAll().size();

        // Create the ContentDoc
        ContentDocDTO contentDocDTO = contentDocMapper.toDto(contentDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentDocMockMvc.perform(put("/api/content-docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentDoc in the database
        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContentDoc in Elasticsearch
        verify(mockContentDocSearchRepository, times(0)).save(contentDoc);
    }

    @Test
    @Transactional
    public void deleteContentDoc() throws Exception {
        // Initialize the database
        contentDocRepository.saveAndFlush(contentDoc);

        int databaseSizeBeforeDelete = contentDocRepository.findAll().size();

        // Delete the contentDoc
        restContentDocMockMvc.perform(delete("/api/content-docs/{id}", contentDoc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentDoc> contentDocList = contentDocRepository.findAll();
        assertThat(contentDocList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContentDoc in Elasticsearch
        verify(mockContentDocSearchRepository, times(1)).deleteById(contentDoc.getId());
    }

    @Test
    @Transactional
    public void searchContentDoc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        contentDocRepository.saveAndFlush(contentDoc);
        when(mockContentDocSearchRepository.search(queryStringQuery("id:" + contentDoc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contentDoc), PageRequest.of(0, 1), 1));

        // Search the contentDoc
        restContentDocMockMvc.perform(get("/api/_search/content-docs?query=id:" + contentDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberContentDoc").value(hasItem(DEFAULT_NUMBER_CONTENT_DOC)))
            .andExpect(jsonPath("$.[*].dataContentDocContentType").value(hasItem(DEFAULT_DATA_CONTENT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataContentDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_CONTENT_DOC))));
    }
}
