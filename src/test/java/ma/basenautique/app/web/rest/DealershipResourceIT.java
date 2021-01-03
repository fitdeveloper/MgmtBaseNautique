package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Dealership;
import ma.basenautique.app.repository.DealershipRepository;
import ma.basenautique.app.repository.search.DealershipSearchRepository;
import ma.basenautique.app.service.DealershipService;
import ma.basenautique.app.service.dto.DealershipDTO;
import ma.basenautique.app.service.mapper.DealershipMapper;

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
 * Integration tests for the {@link DealershipResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DealershipResourceIT {

    private static final String DEFAULT_NUMBER_DEALERSHIP = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_DEALERSHIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE_DEALERSHIP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_DEALERSHIP = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALID_DEALERSHIP = false;
    private static final Boolean UPDATED_VALID_DEALERSHIP = true;

    private static final String DEFAULT_DESC_DEALERSHIP = "AAAAAAAAAA";
    private static final String UPDATED_DESC_DEALERSHIP = "BBBBBBBBBB";

    @Autowired
    private DealershipRepository dealershipRepository;

    @Autowired
    private DealershipMapper dealershipMapper;

    @Autowired
    private DealershipService dealershipService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.DealershipSearchRepositoryMockConfiguration
     */
    @Autowired
    private DealershipSearchRepository mockDealershipSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealershipMockMvc;

    private Dealership dealership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dealership createEntity(EntityManager em) {
        Dealership dealership = new Dealership()
            .numberDealership(DEFAULT_NUMBER_DEALERSHIP)
            .endDateDealership(DEFAULT_END_DATE_DEALERSHIP)
            .validDealership(DEFAULT_VALID_DEALERSHIP)
            .descDealership(DEFAULT_DESC_DEALERSHIP);
        return dealership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dealership createUpdatedEntity(EntityManager em) {
        Dealership dealership = new Dealership()
            .numberDealership(UPDATED_NUMBER_DEALERSHIP)
            .endDateDealership(UPDATED_END_DATE_DEALERSHIP)
            .validDealership(UPDATED_VALID_DEALERSHIP)
            .descDealership(UPDATED_DESC_DEALERSHIP);
        return dealership;
    }

    @BeforeEach
    public void initTest() {
        dealership = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealership() throws Exception {
        int databaseSizeBeforeCreate = dealershipRepository.findAll().size();
        // Create the Dealership
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);
        restDealershipMockMvc.perform(post("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isCreated());

        // Validate the Dealership in the database
        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeCreate + 1);
        Dealership testDealership = dealershipList.get(dealershipList.size() - 1);
        assertThat(testDealership.getNumberDealership()).isEqualTo(DEFAULT_NUMBER_DEALERSHIP);
        assertThat(testDealership.getEndDateDealership()).isEqualTo(DEFAULT_END_DATE_DEALERSHIP);
        assertThat(testDealership.isValidDealership()).isEqualTo(DEFAULT_VALID_DEALERSHIP);
        assertThat(testDealership.getDescDealership()).isEqualTo(DEFAULT_DESC_DEALERSHIP);

        // Validate the Dealership in Elasticsearch
        verify(mockDealershipSearchRepository, times(1)).save(testDealership);
    }

    @Test
    @Transactional
    public void createDealershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealershipRepository.findAll().size();

        // Create the Dealership with an existing ID
        dealership.setId(1L);
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealershipMockMvc.perform(post("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dealership in the database
        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dealership in Elasticsearch
        verify(mockDealershipSearchRepository, times(0)).save(dealership);
    }


    @Test
    @Transactional
    public void checkNumberDealershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealershipRepository.findAll().size();
        // set the field null
        dealership.setNumberDealership(null);

        // Create the Dealership, which fails.
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);


        restDealershipMockMvc.perform(post("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isBadRequest());

        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateDealershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealershipRepository.findAll().size();
        // set the field null
        dealership.setEndDateDealership(null);

        // Create the Dealership, which fails.
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);


        restDealershipMockMvc.perform(post("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isBadRequest());

        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidDealershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealershipRepository.findAll().size();
        // set the field null
        dealership.setValidDealership(null);

        // Create the Dealership, which fails.
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);


        restDealershipMockMvc.perform(post("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isBadRequest());

        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDealerships() throws Exception {
        // Initialize the database
        dealershipRepository.saveAndFlush(dealership);

        // Get all the dealershipList
        restDealershipMockMvc.perform(get("/api/dealerships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealership.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberDealership").value(hasItem(DEFAULT_NUMBER_DEALERSHIP)))
            .andExpect(jsonPath("$.[*].endDateDealership").value(hasItem(DEFAULT_END_DATE_DEALERSHIP.toString())))
            .andExpect(jsonPath("$.[*].validDealership").value(hasItem(DEFAULT_VALID_DEALERSHIP.booleanValue())))
            .andExpect(jsonPath("$.[*].descDealership").value(hasItem(DEFAULT_DESC_DEALERSHIP)));
    }
    
    @Test
    @Transactional
    public void getDealership() throws Exception {
        // Initialize the database
        dealershipRepository.saveAndFlush(dealership);

        // Get the dealership
        restDealershipMockMvc.perform(get("/api/dealerships/{id}", dealership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealership.getId().intValue()))
            .andExpect(jsonPath("$.numberDealership").value(DEFAULT_NUMBER_DEALERSHIP))
            .andExpect(jsonPath("$.endDateDealership").value(DEFAULT_END_DATE_DEALERSHIP.toString()))
            .andExpect(jsonPath("$.validDealership").value(DEFAULT_VALID_DEALERSHIP.booleanValue()))
            .andExpect(jsonPath("$.descDealership").value(DEFAULT_DESC_DEALERSHIP));
    }
    @Test
    @Transactional
    public void getNonExistingDealership() throws Exception {
        // Get the dealership
        restDealershipMockMvc.perform(get("/api/dealerships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealership() throws Exception {
        // Initialize the database
        dealershipRepository.saveAndFlush(dealership);

        int databaseSizeBeforeUpdate = dealershipRepository.findAll().size();

        // Update the dealership
        Dealership updatedDealership = dealershipRepository.findById(dealership.getId()).get();
        // Disconnect from session so that the updates on updatedDealership are not directly saved in db
        em.detach(updatedDealership);
        updatedDealership
            .numberDealership(UPDATED_NUMBER_DEALERSHIP)
            .endDateDealership(UPDATED_END_DATE_DEALERSHIP)
            .validDealership(UPDATED_VALID_DEALERSHIP)
            .descDealership(UPDATED_DESC_DEALERSHIP);
        DealershipDTO dealershipDTO = dealershipMapper.toDto(updatedDealership);

        restDealershipMockMvc.perform(put("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isOk());

        // Validate the Dealership in the database
        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeUpdate);
        Dealership testDealership = dealershipList.get(dealershipList.size() - 1);
        assertThat(testDealership.getNumberDealership()).isEqualTo(UPDATED_NUMBER_DEALERSHIP);
        assertThat(testDealership.getEndDateDealership()).isEqualTo(UPDATED_END_DATE_DEALERSHIP);
        assertThat(testDealership.isValidDealership()).isEqualTo(UPDATED_VALID_DEALERSHIP);
        assertThat(testDealership.getDescDealership()).isEqualTo(UPDATED_DESC_DEALERSHIP);

        // Validate the Dealership in Elasticsearch
        verify(mockDealershipSearchRepository, times(1)).save(testDealership);
    }

    @Test
    @Transactional
    public void updateNonExistingDealership() throws Exception {
        int databaseSizeBeforeUpdate = dealershipRepository.findAll().size();

        // Create the Dealership
        DealershipDTO dealershipDTO = dealershipMapper.toDto(dealership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealershipMockMvc.perform(put("/api/dealerships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealershipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dealership in the database
        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dealership in Elasticsearch
        verify(mockDealershipSearchRepository, times(0)).save(dealership);
    }

    @Test
    @Transactional
    public void deleteDealership() throws Exception {
        // Initialize the database
        dealershipRepository.saveAndFlush(dealership);

        int databaseSizeBeforeDelete = dealershipRepository.findAll().size();

        // Delete the dealership
        restDealershipMockMvc.perform(delete("/api/dealerships/{id}", dealership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dealership> dealershipList = dealershipRepository.findAll();
        assertThat(dealershipList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dealership in Elasticsearch
        verify(mockDealershipSearchRepository, times(1)).deleteById(dealership.getId());
    }

    @Test
    @Transactional
    public void searchDealership() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dealershipRepository.saveAndFlush(dealership);
        when(mockDealershipSearchRepository.search(queryStringQuery("id:" + dealership.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dealership), PageRequest.of(0, 1), 1));

        // Search the dealership
        restDealershipMockMvc.perform(get("/api/_search/dealerships?query=id:" + dealership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealership.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberDealership").value(hasItem(DEFAULT_NUMBER_DEALERSHIP)))
            .andExpect(jsonPath("$.[*].endDateDealership").value(hasItem(DEFAULT_END_DATE_DEALERSHIP.toString())))
            .andExpect(jsonPath("$.[*].validDealership").value(hasItem(DEFAULT_VALID_DEALERSHIP.booleanValue())))
            .andExpect(jsonPath("$.[*].descDealership").value(hasItem(DEFAULT_DESC_DEALERSHIP)));
    }
}
