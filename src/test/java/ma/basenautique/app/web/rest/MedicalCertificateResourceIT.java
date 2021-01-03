package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.MedicalCertificate;
import ma.basenautique.app.repository.MedicalCertificateRepository;
import ma.basenautique.app.repository.search.MedicalCertificateSearchRepository;
import ma.basenautique.app.service.MedicalCertificateService;
import ma.basenautique.app.service.dto.MedicalCertificateDTO;
import ma.basenautique.app.service.mapper.MedicalCertificateMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicalCertificateResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalCertificateResourceIT {

    private static final String DEFAULT_NUMBER_MEDICAL_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_MEDICAL_CERTIFICATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE_MEDICAL_CERTIFICATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_MEDICAL_CERTIFICATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALID_MEDICAL_CERTIFICATE = false;
    private static final Boolean UPDATED_VALID_MEDICAL_CERTIFICATE = true;

    private static final String DEFAULT_DESC_MEDICAL_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_MEDICAL_CERTIFICATE = "BBBBBBBBBB";

    @Autowired
    private MedicalCertificateRepository medicalCertificateRepository;

    @Autowired
    private MedicalCertificateMapper medicalCertificateMapper;

    @Autowired
    private MedicalCertificateService medicalCertificateService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.MedicalCertificateSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalCertificateSearchRepository mockMedicalCertificateSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalCertificateMockMvc;

    private MedicalCertificate medicalCertificate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCertificate createEntity(EntityManager em) {
        MedicalCertificate medicalCertificate = new MedicalCertificate()
            .numberMedicalCertificate(DEFAULT_NUMBER_MEDICAL_CERTIFICATE)
            .endDateMedicalCertificate(DEFAULT_END_DATE_MEDICAL_CERTIFICATE)
            .validMedicalCertificate(DEFAULT_VALID_MEDICAL_CERTIFICATE)
            .descMedicalCertificate(DEFAULT_DESC_MEDICAL_CERTIFICATE);
        return medicalCertificate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCertificate createUpdatedEntity(EntityManager em) {
        MedicalCertificate medicalCertificate = new MedicalCertificate()
            .numberMedicalCertificate(UPDATED_NUMBER_MEDICAL_CERTIFICATE)
            .endDateMedicalCertificate(UPDATED_END_DATE_MEDICAL_CERTIFICATE)
            .validMedicalCertificate(UPDATED_VALID_MEDICAL_CERTIFICATE)
            .descMedicalCertificate(UPDATED_DESC_MEDICAL_CERTIFICATE);
        return medicalCertificate;
    }

    @BeforeEach
    public void initTest() {
        medicalCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalCertificate() throws Exception {
        int databaseSizeBeforeCreate = medicalCertificateRepository.findAll().size();
        // Create the MedicalCertificate
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);
        restMedicalCertificateMockMvc.perform(post("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalCertificate in the database
        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalCertificate testMedicalCertificate = medicalCertificateList.get(medicalCertificateList.size() - 1);
        assertThat(testMedicalCertificate.getNumberMedicalCertificate()).isEqualTo(DEFAULT_NUMBER_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.getEndDateMedicalCertificate()).isEqualTo(DEFAULT_END_DATE_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.isValidMedicalCertificate()).isEqualTo(DEFAULT_VALID_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.getDescMedicalCertificate()).isEqualTo(DEFAULT_DESC_MEDICAL_CERTIFICATE);

        // Validate the MedicalCertificate in Elasticsearch
        verify(mockMedicalCertificateSearchRepository, times(1)).save(testMedicalCertificate);
    }

    @Test
    @Transactional
    public void createMedicalCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalCertificateRepository.findAll().size();

        // Create the MedicalCertificate with an existing ID
        medicalCertificate.setId(1L);
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalCertificateMockMvc.perform(post("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificate in the database
        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalCertificate in Elasticsearch
        verify(mockMedicalCertificateSearchRepository, times(0)).save(medicalCertificate);
    }


    @Test
    @Transactional
    public void checkNumberMedicalCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalCertificateRepository.findAll().size();
        // set the field null
        medicalCertificate.setNumberMedicalCertificate(null);

        // Create the MedicalCertificate, which fails.
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);


        restMedicalCertificateMockMvc.perform(post("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateMedicalCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalCertificateRepository.findAll().size();
        // set the field null
        medicalCertificate.setEndDateMedicalCertificate(null);

        // Create the MedicalCertificate, which fails.
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);


        restMedicalCertificateMockMvc.perform(post("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidMedicalCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalCertificateRepository.findAll().size();
        // set the field null
        medicalCertificate.setValidMedicalCertificate(null);

        // Create the MedicalCertificate, which fails.
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);


        restMedicalCertificateMockMvc.perform(post("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalCertificates() throws Exception {
        // Initialize the database
        medicalCertificateRepository.saveAndFlush(medicalCertificate);

        // Get all the medicalCertificateList
        restMedicalCertificateMockMvc.perform(get("/api/medical-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMedicalCertificate").value(hasItem(DEFAULT_NUMBER_MEDICAL_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].endDateMedicalCertificate").value(hasItem(DEFAULT_END_DATE_MEDICAL_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].validMedicalCertificate").value(hasItem(DEFAULT_VALID_MEDICAL_CERTIFICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].descMedicalCertificate").value(hasItem(DEFAULT_DESC_MEDICAL_CERTIFICATE)));
    }
    
    @Test
    @Transactional
    public void getMedicalCertificate() throws Exception {
        // Initialize the database
        medicalCertificateRepository.saveAndFlush(medicalCertificate);

        // Get the medicalCertificate
        restMedicalCertificateMockMvc.perform(get("/api/medical-certificates/{id}", medicalCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalCertificate.getId().intValue()))
            .andExpect(jsonPath("$.numberMedicalCertificate").value(DEFAULT_NUMBER_MEDICAL_CERTIFICATE))
            .andExpect(jsonPath("$.endDateMedicalCertificate").value(DEFAULT_END_DATE_MEDICAL_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.validMedicalCertificate").value(DEFAULT_VALID_MEDICAL_CERTIFICATE.booleanValue()))
            .andExpect(jsonPath("$.descMedicalCertificate").value(DEFAULT_DESC_MEDICAL_CERTIFICATE));
    }
    @Test
    @Transactional
    public void getNonExistingMedicalCertificate() throws Exception {
        // Get the medicalCertificate
        restMedicalCertificateMockMvc.perform(get("/api/medical-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalCertificate() throws Exception {
        // Initialize the database
        medicalCertificateRepository.saveAndFlush(medicalCertificate);

        int databaseSizeBeforeUpdate = medicalCertificateRepository.findAll().size();

        // Update the medicalCertificate
        MedicalCertificate updatedMedicalCertificate = medicalCertificateRepository.findById(medicalCertificate.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalCertificate are not directly saved in db
        em.detach(updatedMedicalCertificate);
        updatedMedicalCertificate
            .numberMedicalCertificate(UPDATED_NUMBER_MEDICAL_CERTIFICATE)
            .endDateMedicalCertificate(UPDATED_END_DATE_MEDICAL_CERTIFICATE)
            .validMedicalCertificate(UPDATED_VALID_MEDICAL_CERTIFICATE)
            .descMedicalCertificate(UPDATED_DESC_MEDICAL_CERTIFICATE);
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(updatedMedicalCertificate);

        restMedicalCertificateMockMvc.perform(put("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalCertificate in the database
        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeUpdate);
        MedicalCertificate testMedicalCertificate = medicalCertificateList.get(medicalCertificateList.size() - 1);
        assertThat(testMedicalCertificate.getNumberMedicalCertificate()).isEqualTo(UPDATED_NUMBER_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.getEndDateMedicalCertificate()).isEqualTo(UPDATED_END_DATE_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.isValidMedicalCertificate()).isEqualTo(UPDATED_VALID_MEDICAL_CERTIFICATE);
        assertThat(testMedicalCertificate.getDescMedicalCertificate()).isEqualTo(UPDATED_DESC_MEDICAL_CERTIFICATE);

        // Validate the MedicalCertificate in Elasticsearch
        verify(mockMedicalCertificateSearchRepository, times(1)).save(testMedicalCertificate);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalCertificate() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificateRepository.findAll().size();

        // Create the MedicalCertificate
        MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(medicalCertificate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalCertificateMockMvc.perform(put("/api/medical-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCertificateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificate in the database
        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalCertificate in Elasticsearch
        verify(mockMedicalCertificateSearchRepository, times(0)).save(medicalCertificate);
    }

    @Test
    @Transactional
    public void deleteMedicalCertificate() throws Exception {
        // Initialize the database
        medicalCertificateRepository.saveAndFlush(medicalCertificate);

        int databaseSizeBeforeDelete = medicalCertificateRepository.findAll().size();

        // Delete the medicalCertificate
        restMedicalCertificateMockMvc.perform(delete("/api/medical-certificates/{id}", medicalCertificate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalCertificate> medicalCertificateList = medicalCertificateRepository.findAll();
        assertThat(medicalCertificateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalCertificate in Elasticsearch
        verify(mockMedicalCertificateSearchRepository, times(1)).deleteById(medicalCertificate.getId());
    }

    @Test
    @Transactional
    public void searchMedicalCertificate() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        medicalCertificateRepository.saveAndFlush(medicalCertificate);
        when(mockMedicalCertificateSearchRepository.search(queryStringQuery("id:" + medicalCertificate.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicalCertificate), PageRequest.of(0, 1), 1));

        // Search the medicalCertificate
        restMedicalCertificateMockMvc.perform(get("/api/_search/medical-certificates?query=id:" + medicalCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMedicalCertificate").value(hasItem(DEFAULT_NUMBER_MEDICAL_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].endDateMedicalCertificate").value(hasItem(DEFAULT_END_DATE_MEDICAL_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].validMedicalCertificate").value(hasItem(DEFAULT_VALID_MEDICAL_CERTIFICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].descMedicalCertificate").value(hasItem(DEFAULT_DESC_MEDICAL_CERTIFICATE)));
    }
}
