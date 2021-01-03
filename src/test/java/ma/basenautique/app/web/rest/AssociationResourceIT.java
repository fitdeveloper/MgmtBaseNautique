package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Association;
import ma.basenautique.app.domain.Member;
import ma.basenautique.app.repository.AssociationRepository;
import ma.basenautique.app.repository.search.AssociationSearchRepository;
import ma.basenautique.app.service.AssociationService;
import ma.basenautique.app.service.dto.AssociationDTO;
import ma.basenautique.app.service.mapper.AssociationMapper;

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
 * Integration tests for the {@link AssociationResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssociationResourceIT {

    private static final String DEFAULT_NUMBER_ASSOCIATION = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_ASSOCIATION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ASSOCIATION = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ASSOCIATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_ASSOCIATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_ASSOCIATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_ASSOCIATION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESC_ASSOCIATION = "AAAAAAAAAA";
    private static final String UPDATED_DESC_ASSOCIATION = "BBBBBBBBBB";

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private AssociationMapper associationMapper;

    @Autowired
    private AssociationService associationService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.AssociationSearchRepositoryMockConfiguration
     */
    @Autowired
    private AssociationSearchRepository mockAssociationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssociationMockMvc;

    private Association association;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Association createEntity(EntityManager em) {
        Association association = new Association()
            .numberAssociation(DEFAULT_NUMBER_ASSOCIATION)
            .nameAssociation(DEFAULT_NAME_ASSOCIATION)
            .imageAssociation(DEFAULT_IMAGE_ASSOCIATION)
            .imageAssociationContentType(DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE)
            .descAssociation(DEFAULT_DESC_ASSOCIATION);
        // Add required entity
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            member = MemberResourceIT.createEntity(em);
            em.persist(member);
            em.flush();
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        association.getMembers().add(member);
        return association;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Association createUpdatedEntity(EntityManager em) {
        Association association = new Association()
            .numberAssociation(UPDATED_NUMBER_ASSOCIATION)
            .nameAssociation(UPDATED_NAME_ASSOCIATION)
            .imageAssociation(UPDATED_IMAGE_ASSOCIATION)
            .imageAssociationContentType(UPDATED_IMAGE_ASSOCIATION_CONTENT_TYPE)
            .descAssociation(UPDATED_DESC_ASSOCIATION);
        // Add required entity
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            member = MemberResourceIT.createUpdatedEntity(em);
            em.persist(member);
            em.flush();
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        association.getMembers().add(member);
        return association;
    }

    @BeforeEach
    public void initTest() {
        association = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssociation() throws Exception {
        int databaseSizeBeforeCreate = associationRepository.findAll().size();
        // Create the Association
        AssociationDTO associationDTO = associationMapper.toDto(association);
        restAssociationMockMvc.perform(post("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isCreated());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeCreate + 1);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getNumberAssociation()).isEqualTo(DEFAULT_NUMBER_ASSOCIATION);
        assertThat(testAssociation.getNameAssociation()).isEqualTo(DEFAULT_NAME_ASSOCIATION);
        assertThat(testAssociation.getImageAssociation()).isEqualTo(DEFAULT_IMAGE_ASSOCIATION);
        assertThat(testAssociation.getImageAssociationContentType()).isEqualTo(DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE);
        assertThat(testAssociation.getDescAssociation()).isEqualTo(DEFAULT_DESC_ASSOCIATION);

        // Validate the Association in Elasticsearch
        verify(mockAssociationSearchRepository, times(1)).save(testAssociation);
    }

    @Test
    @Transactional
    public void createAssociationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = associationRepository.findAll().size();

        // Create the Association with an existing ID
        association.setId(1L);
        AssociationDTO associationDTO = associationMapper.toDto(association);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociationMockMvc.perform(post("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Association in Elasticsearch
        verify(mockAssociationSearchRepository, times(0)).save(association);
    }


    @Test
    @Transactional
    public void checkNumberAssociationIsRequired() throws Exception {
        int databaseSizeBeforeTest = associationRepository.findAll().size();
        // set the field null
        association.setNumberAssociation(null);

        // Create the Association, which fails.
        AssociationDTO associationDTO = associationMapper.toDto(association);


        restAssociationMockMvc.perform(post("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isBadRequest());

        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameAssociationIsRequired() throws Exception {
        int databaseSizeBeforeTest = associationRepository.findAll().size();
        // set the field null
        association.setNameAssociation(null);

        // Create the Association, which fails.
        AssociationDTO associationDTO = associationMapper.toDto(association);


        restAssociationMockMvc.perform(post("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isBadRequest());

        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssociations() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        // Get all the associationList
        restAssociationMockMvc.perform(get("/api/associations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(association.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberAssociation").value(hasItem(DEFAULT_NUMBER_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].nameAssociation").value(hasItem(DEFAULT_NAME_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].imageAssociationContentType").value(hasItem(DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageAssociation").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_ASSOCIATION))))
            .andExpect(jsonPath("$.[*].descAssociation").value(hasItem(DEFAULT_DESC_ASSOCIATION)));
    }
    
    @Test
    @Transactional
    public void getAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        // Get the association
        restAssociationMockMvc.perform(get("/api/associations/{id}", association.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(association.getId().intValue()))
            .andExpect(jsonPath("$.numberAssociation").value(DEFAULT_NUMBER_ASSOCIATION))
            .andExpect(jsonPath("$.nameAssociation").value(DEFAULT_NAME_ASSOCIATION))
            .andExpect(jsonPath("$.imageAssociationContentType").value(DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageAssociation").value(Base64Utils.encodeToString(DEFAULT_IMAGE_ASSOCIATION)))
            .andExpect(jsonPath("$.descAssociation").value(DEFAULT_DESC_ASSOCIATION));
    }
    @Test
    @Transactional
    public void getNonExistingAssociation() throws Exception {
        // Get the association
        restAssociationMockMvc.perform(get("/api/associations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeUpdate = associationRepository.findAll().size();

        // Update the association
        Association updatedAssociation = associationRepository.findById(association.getId()).get();
        // Disconnect from session so that the updates on updatedAssociation are not directly saved in db
        em.detach(updatedAssociation);
        updatedAssociation
            .numberAssociation(UPDATED_NUMBER_ASSOCIATION)
            .nameAssociation(UPDATED_NAME_ASSOCIATION)
            .imageAssociation(UPDATED_IMAGE_ASSOCIATION)
            .imageAssociationContentType(UPDATED_IMAGE_ASSOCIATION_CONTENT_TYPE)
            .descAssociation(UPDATED_DESC_ASSOCIATION);
        AssociationDTO associationDTO = associationMapper.toDto(updatedAssociation);

        restAssociationMockMvc.perform(put("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isOk());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getNumberAssociation()).isEqualTo(UPDATED_NUMBER_ASSOCIATION);
        assertThat(testAssociation.getNameAssociation()).isEqualTo(UPDATED_NAME_ASSOCIATION);
        assertThat(testAssociation.getImageAssociation()).isEqualTo(UPDATED_IMAGE_ASSOCIATION);
        assertThat(testAssociation.getImageAssociationContentType()).isEqualTo(UPDATED_IMAGE_ASSOCIATION_CONTENT_TYPE);
        assertThat(testAssociation.getDescAssociation()).isEqualTo(UPDATED_DESC_ASSOCIATION);

        // Validate the Association in Elasticsearch
        verify(mockAssociationSearchRepository, times(1)).save(testAssociation);
    }

    @Test
    @Transactional
    public void updateNonExistingAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();

        // Create the Association
        AssociationDTO associationDTO = associationMapper.toDto(association);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociationMockMvc.perform(put("/api/associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Association in Elasticsearch
        verify(mockAssociationSearchRepository, times(0)).save(association);
    }

    @Test
    @Transactional
    public void deleteAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeDelete = associationRepository.findAll().size();

        // Delete the association
        restAssociationMockMvc.perform(delete("/api/associations/{id}", association.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Association in Elasticsearch
        verify(mockAssociationSearchRepository, times(1)).deleteById(association.getId());
    }

    @Test
    @Transactional
    public void searchAssociation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        associationRepository.saveAndFlush(association);
        when(mockAssociationSearchRepository.search(queryStringQuery("id:" + association.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(association), PageRequest.of(0, 1), 1));

        // Search the association
        restAssociationMockMvc.perform(get("/api/_search/associations?query=id:" + association.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(association.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberAssociation").value(hasItem(DEFAULT_NUMBER_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].nameAssociation").value(hasItem(DEFAULT_NAME_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].imageAssociationContentType").value(hasItem(DEFAULT_IMAGE_ASSOCIATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageAssociation").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_ASSOCIATION))))
            .andExpect(jsonPath("$.[*].descAssociation").value(hasItem(DEFAULT_DESC_ASSOCIATION)));
    }
}
