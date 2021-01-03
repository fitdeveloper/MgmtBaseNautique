package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Insurance;
import ma.basenautique.app.repository.InsuranceRepository;
import ma.basenautique.app.repository.search.InsuranceSearchRepository;
import ma.basenautique.app.service.InsuranceService;
import ma.basenautique.app.service.dto.InsuranceDTO;
import ma.basenautique.app.service.mapper.InsuranceMapper;

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
 * Integration tests for the {@link InsuranceResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InsuranceResourceIT {

    private static final String DEFAULT_NUMBER_INSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_INSURANCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE_INSURANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_INSURANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALID_INSURANCE = false;
    private static final Boolean UPDATED_VALID_INSURANCE = true;

    private static final String DEFAULT_DESC_INSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_INSURANCE = "BBBBBBBBBB";

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private InsuranceMapper insuranceMapper;

    @Autowired
    private InsuranceService insuranceService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.InsuranceSearchRepositoryMockConfiguration
     */
    @Autowired
    private InsuranceSearchRepository mockInsuranceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsuranceMockMvc;

    private Insurance insurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurance createEntity(EntityManager em) {
        Insurance insurance = new Insurance()
            .numberInsurance(DEFAULT_NUMBER_INSURANCE)
            .endDateInsurance(DEFAULT_END_DATE_INSURANCE)
            .validInsurance(DEFAULT_VALID_INSURANCE)
            .descInsurance(DEFAULT_DESC_INSURANCE);
        return insurance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurance createUpdatedEntity(EntityManager em) {
        Insurance insurance = new Insurance()
            .numberInsurance(UPDATED_NUMBER_INSURANCE)
            .endDateInsurance(UPDATED_END_DATE_INSURANCE)
            .validInsurance(UPDATED_VALID_INSURANCE)
            .descInsurance(UPDATED_DESC_INSURANCE);
        return insurance;
    }

    @BeforeEach
    public void initTest() {
        insurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurance() throws Exception {
        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();
        // Create the Insurance
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);
        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate + 1);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getNumberInsurance()).isEqualTo(DEFAULT_NUMBER_INSURANCE);
        assertThat(testInsurance.getEndDateInsurance()).isEqualTo(DEFAULT_END_DATE_INSURANCE);
        assertThat(testInsurance.isValidInsurance()).isEqualTo(DEFAULT_VALID_INSURANCE);
        assertThat(testInsurance.getDescInsurance()).isEqualTo(DEFAULT_DESC_INSURANCE);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).save(testInsurance);
    }

    @Test
    @Transactional
    public void createInsuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();

        // Create the Insurance with an existing ID
        insurance.setId(1L);
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(0)).save(insurance);
    }


    @Test
    @Transactional
    public void checkNumberInsuranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceRepository.findAll().size();
        // set the field null
        insurance.setNumberInsurance(null);

        // Create the Insurance, which fails.
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);


        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateInsuranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceRepository.findAll().size();
        // set the field null
        insurance.setEndDateInsurance(null);

        // Create the Insurance, which fails.
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);


        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidInsuranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceRepository.findAll().size();
        // set the field null
        insurance.setValidInsurance(null);

        // Create the Insurance, which fails.
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);


        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInsurances() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get all the insuranceList
        restInsuranceMockMvc.perform(get("/api/insurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberInsurance").value(hasItem(DEFAULT_NUMBER_INSURANCE)))
            .andExpect(jsonPath("$.[*].endDateInsurance").value(hasItem(DEFAULT_END_DATE_INSURANCE.toString())))
            .andExpect(jsonPath("$.[*].validInsurance").value(hasItem(DEFAULT_VALID_INSURANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].descInsurance").value(hasItem(DEFAULT_DESC_INSURANCE)));
    }
    
    @Test
    @Transactional
    public void getInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get the insurance
        restInsuranceMockMvc.perform(get("/api/insurances/{id}", insurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insurance.getId().intValue()))
            .andExpect(jsonPath("$.numberInsurance").value(DEFAULT_NUMBER_INSURANCE))
            .andExpect(jsonPath("$.endDateInsurance").value(DEFAULT_END_DATE_INSURANCE.toString()))
            .andExpect(jsonPath("$.validInsurance").value(DEFAULT_VALID_INSURANCE.booleanValue()))
            .andExpect(jsonPath("$.descInsurance").value(DEFAULT_DESC_INSURANCE));
    }
    @Test
    @Transactional
    public void getNonExistingInsurance() throws Exception {
        // Get the insurance
        restInsuranceMockMvc.perform(get("/api/insurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Update the insurance
        Insurance updatedInsurance = insuranceRepository.findById(insurance.getId()).get();
        // Disconnect from session so that the updates on updatedInsurance are not directly saved in db
        em.detach(updatedInsurance);
        updatedInsurance
            .numberInsurance(UPDATED_NUMBER_INSURANCE)
            .endDateInsurance(UPDATED_END_DATE_INSURANCE)
            .validInsurance(UPDATED_VALID_INSURANCE)
            .descInsurance(UPDATED_DESC_INSURANCE);
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(updatedInsurance);

        restInsuranceMockMvc.perform(put("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isOk());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getNumberInsurance()).isEqualTo(UPDATED_NUMBER_INSURANCE);
        assertThat(testInsurance.getEndDateInsurance()).isEqualTo(UPDATED_END_DATE_INSURANCE);
        assertThat(testInsurance.isValidInsurance()).isEqualTo(UPDATED_VALID_INSURANCE);
        assertThat(testInsurance.getDescInsurance()).isEqualTo(UPDATED_DESC_INSURANCE);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).save(testInsurance);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Create the Insurance
        InsuranceDTO insuranceDTO = insuranceMapper.toDto(insurance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceMockMvc.perform(put("/api/insurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(0)).save(insurance);
    }

    @Test
    @Transactional
    public void deleteInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeDelete = insuranceRepository.findAll().size();

        // Delete the insurance
        restInsuranceMockMvc.perform(delete("/api/insurances/{id}", insurance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).deleteById(insurance.getId());
    }

    @Test
    @Transactional
    public void searchInsurance() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);
        when(mockInsuranceSearchRepository.search(queryStringQuery("id:" + insurance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(insurance), PageRequest.of(0, 1), 1));

        // Search the insurance
        restInsuranceMockMvc.perform(get("/api/_search/insurances?query=id:" + insurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberInsurance").value(hasItem(DEFAULT_NUMBER_INSURANCE)))
            .andExpect(jsonPath("$.[*].endDateInsurance").value(hasItem(DEFAULT_END_DATE_INSURANCE.toString())))
            .andExpect(jsonPath("$.[*].validInsurance").value(hasItem(DEFAULT_VALID_INSURANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].descInsurance").value(hasItem(DEFAULT_DESC_INSURANCE)));
    }
}
