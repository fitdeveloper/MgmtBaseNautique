package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Doc;
import ma.basenautique.app.domain.ContentDoc;
import ma.basenautique.app.domain.TypeDoc;
import ma.basenautique.app.repository.DocRepository;
import ma.basenautique.app.repository.search.DocSearchRepository;
import ma.basenautique.app.service.DocService;
import ma.basenautique.app.service.dto.DocDTO;
import ma.basenautique.app.service.mapper.DocMapper;

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
 * Integration tests for the {@link DocResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocResourceIT {

    private static final String DEFAULT_NUMBER_DOC = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_DOC = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE_DOC = 1L;
    private static final Long UPDATED_SIZE_DOC = 2L;

    private static final String DEFAULT_MIME_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_DOC = "AAAAAAAAAA";
    private static final String UPDATED_DESC_DOC = "BBBBBBBBBB";

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private DocMapper docMapper;

    @Autowired
    private DocService docService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.DocSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocSearchRepository mockDocSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocMockMvc;

    private Doc doc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doc createEntity(EntityManager em) {
        Doc doc = new Doc()
            .numberDoc(DEFAULT_NUMBER_DOC)
            .titleDoc(DEFAULT_TITLE_DOC)
            .sizeDoc(DEFAULT_SIZE_DOC)
            .mimeTypeDoc(DEFAULT_MIME_TYPE_DOC)
            .descDoc(DEFAULT_DESC_DOC);
        // Add required entity
        ContentDoc contentDoc;
        if (TestUtil.findAll(em, ContentDoc.class).isEmpty()) {
            contentDoc = ContentDocResourceIT.createEntity(em);
            em.persist(contentDoc);
            em.flush();
        } else {
            contentDoc = TestUtil.findAll(em, ContentDoc.class).get(0);
        }
        doc.setContentDoc(contentDoc);
        // Add required entity
        TypeDoc typeDoc;
        if (TestUtil.findAll(em, TypeDoc.class).isEmpty()) {
            typeDoc = TypeDocResourceIT.createEntity(em);
            em.persist(typeDoc);
            em.flush();
        } else {
            typeDoc = TestUtil.findAll(em, TypeDoc.class).get(0);
        }
        doc.setTypeDoc(typeDoc);
        return doc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doc createUpdatedEntity(EntityManager em) {
        Doc doc = new Doc()
            .numberDoc(UPDATED_NUMBER_DOC)
            .titleDoc(UPDATED_TITLE_DOC)
            .sizeDoc(UPDATED_SIZE_DOC)
            .mimeTypeDoc(UPDATED_MIME_TYPE_DOC)
            .descDoc(UPDATED_DESC_DOC);
        // Add required entity
        ContentDoc contentDoc;
        if (TestUtil.findAll(em, ContentDoc.class).isEmpty()) {
            contentDoc = ContentDocResourceIT.createUpdatedEntity(em);
            em.persist(contentDoc);
            em.flush();
        } else {
            contentDoc = TestUtil.findAll(em, ContentDoc.class).get(0);
        }
        doc.setContentDoc(contentDoc);
        // Add required entity
        TypeDoc typeDoc;
        if (TestUtil.findAll(em, TypeDoc.class).isEmpty()) {
            typeDoc = TypeDocResourceIT.createUpdatedEntity(em);
            em.persist(typeDoc);
            em.flush();
        } else {
            typeDoc = TestUtil.findAll(em, TypeDoc.class).get(0);
        }
        doc.setTypeDoc(typeDoc);
        return doc;
    }

    @BeforeEach
    public void initTest() {
        doc = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoc() throws Exception {
        int databaseSizeBeforeCreate = docRepository.findAll().size();
        // Create the Doc
        DocDTO docDTO = docMapper.toDto(doc);
        restDocMockMvc.perform(post("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isCreated());

        // Validate the Doc in the database
        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeCreate + 1);
        Doc testDoc = docList.get(docList.size() - 1);
        assertThat(testDoc.getNumberDoc()).isEqualTo(DEFAULT_NUMBER_DOC);
        assertThat(testDoc.getTitleDoc()).isEqualTo(DEFAULT_TITLE_DOC);
        assertThat(testDoc.getSizeDoc()).isEqualTo(DEFAULT_SIZE_DOC);
        assertThat(testDoc.getMimeTypeDoc()).isEqualTo(DEFAULT_MIME_TYPE_DOC);
        assertThat(testDoc.getDescDoc()).isEqualTo(DEFAULT_DESC_DOC);

        // Validate the Doc in Elasticsearch
        verify(mockDocSearchRepository, times(1)).save(testDoc);
    }

    @Test
    @Transactional
    public void createDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docRepository.findAll().size();

        // Create the Doc with an existing ID
        doc.setId(1L);
        DocDTO docDTO = docMapper.toDto(doc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocMockMvc.perform(post("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doc in the database
        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeCreate);

        // Validate the Doc in Elasticsearch
        verify(mockDocSearchRepository, times(0)).save(doc);
    }


    @Test
    @Transactional
    public void checkNumberDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = docRepository.findAll().size();
        // set the field null
        doc.setNumberDoc(null);

        // Create the Doc, which fails.
        DocDTO docDTO = docMapper.toDto(doc);


        restDocMockMvc.perform(post("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isBadRequest());

        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = docRepository.findAll().size();
        // set the field null
        doc.setTitleDoc(null);

        // Create the Doc, which fails.
        DocDTO docDTO = docMapper.toDto(doc);


        restDocMockMvc.perform(post("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isBadRequest());

        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = docRepository.findAll().size();
        // set the field null
        doc.setSizeDoc(null);

        // Create the Doc, which fails.
        DocDTO docDTO = docMapper.toDto(doc);


        restDocMockMvc.perform(post("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isBadRequest());

        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocs() throws Exception {
        // Initialize the database
        docRepository.saveAndFlush(doc);

        // Get all the docList
        restDocMockMvc.perform(get("/api/docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberDoc").value(hasItem(DEFAULT_NUMBER_DOC)))
            .andExpect(jsonPath("$.[*].titleDoc").value(hasItem(DEFAULT_TITLE_DOC)))
            .andExpect(jsonPath("$.[*].sizeDoc").value(hasItem(DEFAULT_SIZE_DOC.intValue())))
            .andExpect(jsonPath("$.[*].mimeTypeDoc").value(hasItem(DEFAULT_MIME_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].descDoc").value(hasItem(DEFAULT_DESC_DOC)));
    }
    
    @Test
    @Transactional
    public void getDoc() throws Exception {
        // Initialize the database
        docRepository.saveAndFlush(doc);

        // Get the doc
        restDocMockMvc.perform(get("/api/docs/{id}", doc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doc.getId().intValue()))
            .andExpect(jsonPath("$.numberDoc").value(DEFAULT_NUMBER_DOC))
            .andExpect(jsonPath("$.titleDoc").value(DEFAULT_TITLE_DOC))
            .andExpect(jsonPath("$.sizeDoc").value(DEFAULT_SIZE_DOC.intValue()))
            .andExpect(jsonPath("$.mimeTypeDoc").value(DEFAULT_MIME_TYPE_DOC))
            .andExpect(jsonPath("$.descDoc").value(DEFAULT_DESC_DOC));
    }
    @Test
    @Transactional
    public void getNonExistingDoc() throws Exception {
        // Get the doc
        restDocMockMvc.perform(get("/api/docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoc() throws Exception {
        // Initialize the database
        docRepository.saveAndFlush(doc);

        int databaseSizeBeforeUpdate = docRepository.findAll().size();

        // Update the doc
        Doc updatedDoc = docRepository.findById(doc.getId()).get();
        // Disconnect from session so that the updates on updatedDoc are not directly saved in db
        em.detach(updatedDoc);
        updatedDoc
            .numberDoc(UPDATED_NUMBER_DOC)
            .titleDoc(UPDATED_TITLE_DOC)
            .sizeDoc(UPDATED_SIZE_DOC)
            .mimeTypeDoc(UPDATED_MIME_TYPE_DOC)
            .descDoc(UPDATED_DESC_DOC);
        DocDTO docDTO = docMapper.toDto(updatedDoc);

        restDocMockMvc.perform(put("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isOk());

        // Validate the Doc in the database
        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeUpdate);
        Doc testDoc = docList.get(docList.size() - 1);
        assertThat(testDoc.getNumberDoc()).isEqualTo(UPDATED_NUMBER_DOC);
        assertThat(testDoc.getTitleDoc()).isEqualTo(UPDATED_TITLE_DOC);
        assertThat(testDoc.getSizeDoc()).isEqualTo(UPDATED_SIZE_DOC);
        assertThat(testDoc.getMimeTypeDoc()).isEqualTo(UPDATED_MIME_TYPE_DOC);
        assertThat(testDoc.getDescDoc()).isEqualTo(UPDATED_DESC_DOC);

        // Validate the Doc in Elasticsearch
        verify(mockDocSearchRepository, times(1)).save(testDoc);
    }

    @Test
    @Transactional
    public void updateNonExistingDoc() throws Exception {
        int databaseSizeBeforeUpdate = docRepository.findAll().size();

        // Create the Doc
        DocDTO docDTO = docMapper.toDto(doc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocMockMvc.perform(put("/api/docs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doc in the database
        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Doc in Elasticsearch
        verify(mockDocSearchRepository, times(0)).save(doc);
    }

    @Test
    @Transactional
    public void deleteDoc() throws Exception {
        // Initialize the database
        docRepository.saveAndFlush(doc);

        int databaseSizeBeforeDelete = docRepository.findAll().size();

        // Delete the doc
        restDocMockMvc.perform(delete("/api/docs/{id}", doc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doc> docList = docRepository.findAll();
        assertThat(docList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Doc in Elasticsearch
        verify(mockDocSearchRepository, times(1)).deleteById(doc.getId());
    }

    @Test
    @Transactional
    public void searchDoc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        docRepository.saveAndFlush(doc);
        when(mockDocSearchRepository.search(queryStringQuery("id:" + doc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(doc), PageRequest.of(0, 1), 1));

        // Search the doc
        restDocMockMvc.perform(get("/api/_search/docs?query=id:" + doc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doc.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberDoc").value(hasItem(DEFAULT_NUMBER_DOC)))
            .andExpect(jsonPath("$.[*].titleDoc").value(hasItem(DEFAULT_TITLE_DOC)))
            .andExpect(jsonPath("$.[*].sizeDoc").value(hasItem(DEFAULT_SIZE_DOC.intValue())))
            .andExpect(jsonPath("$.[*].mimeTypeDoc").value(hasItem(DEFAULT_MIME_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].descDoc").value(hasItem(DEFAULT_DESC_DOC)));
    }
}
