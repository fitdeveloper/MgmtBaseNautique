package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.CongePolice;
import ma.basenautique.app.repository.CongePoliceRepository;
import ma.basenautique.app.repository.search.CongePoliceSearchRepository;
import ma.basenautique.app.service.CongePoliceService;
import ma.basenautique.app.service.dto.CongePoliceDTO;
import ma.basenautique.app.service.mapper.CongePoliceMapper;

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
 * Integration tests for the {@link CongePoliceResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CongePoliceResourceIT {

    private static final String DEFAULT_NUMBER_CONGE_POLICE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_CONGE_POLICE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE_CONGE_POLICE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_CONGE_POLICE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALID_CONGE_POLICE = false;
    private static final Boolean UPDATED_VALID_CONGE_POLICE = true;

    private static final String DEFAULT_DESC_CONGE_POLICE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_CONGE_POLICE = "BBBBBBBBBB";

    @Autowired
    private CongePoliceRepository congePoliceRepository;

    @Autowired
    private CongePoliceMapper congePoliceMapper;

    @Autowired
    private CongePoliceService congePoliceService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.CongePoliceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CongePoliceSearchRepository mockCongePoliceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCongePoliceMockMvc;

    private CongePolice congePolice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CongePolice createEntity(EntityManager em) {
        CongePolice congePolice = new CongePolice()
            .numberCongePolice(DEFAULT_NUMBER_CONGE_POLICE)
            .endDateCongePolice(DEFAULT_END_DATE_CONGE_POLICE)
            .validCongePolice(DEFAULT_VALID_CONGE_POLICE)
            .descCongePolice(DEFAULT_DESC_CONGE_POLICE);
        return congePolice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CongePolice createUpdatedEntity(EntityManager em) {
        CongePolice congePolice = new CongePolice()
            .numberCongePolice(UPDATED_NUMBER_CONGE_POLICE)
            .endDateCongePolice(UPDATED_END_DATE_CONGE_POLICE)
            .validCongePolice(UPDATED_VALID_CONGE_POLICE)
            .descCongePolice(UPDATED_DESC_CONGE_POLICE);
        return congePolice;
    }

    @BeforeEach
    public void initTest() {
        congePolice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCongePolice() throws Exception {
        int databaseSizeBeforeCreate = congePoliceRepository.findAll().size();
        // Create the CongePolice
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);
        restCongePoliceMockMvc.perform(post("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isCreated());

        // Validate the CongePolice in the database
        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeCreate + 1);
        CongePolice testCongePolice = congePoliceList.get(congePoliceList.size() - 1);
        assertThat(testCongePolice.getNumberCongePolice()).isEqualTo(DEFAULT_NUMBER_CONGE_POLICE);
        assertThat(testCongePolice.getEndDateCongePolice()).isEqualTo(DEFAULT_END_DATE_CONGE_POLICE);
        assertThat(testCongePolice.isValidCongePolice()).isEqualTo(DEFAULT_VALID_CONGE_POLICE);
        assertThat(testCongePolice.getDescCongePolice()).isEqualTo(DEFAULT_DESC_CONGE_POLICE);

        // Validate the CongePolice in Elasticsearch
        verify(mockCongePoliceSearchRepository, times(1)).save(testCongePolice);
    }

    @Test
    @Transactional
    public void createCongePoliceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = congePoliceRepository.findAll().size();

        // Create the CongePolice with an existing ID
        congePolice.setId(1L);
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCongePoliceMockMvc.perform(post("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CongePolice in the database
        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CongePolice in Elasticsearch
        verify(mockCongePoliceSearchRepository, times(0)).save(congePolice);
    }


    @Test
    @Transactional
    public void checkNumberCongePoliceIsRequired() throws Exception {
        int databaseSizeBeforeTest = congePoliceRepository.findAll().size();
        // set the field null
        congePolice.setNumberCongePolice(null);

        // Create the CongePolice, which fails.
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);


        restCongePoliceMockMvc.perform(post("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isBadRequest());

        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateCongePoliceIsRequired() throws Exception {
        int databaseSizeBeforeTest = congePoliceRepository.findAll().size();
        // set the field null
        congePolice.setEndDateCongePolice(null);

        // Create the CongePolice, which fails.
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);


        restCongePoliceMockMvc.perform(post("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isBadRequest());

        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidCongePoliceIsRequired() throws Exception {
        int databaseSizeBeforeTest = congePoliceRepository.findAll().size();
        // set the field null
        congePolice.setValidCongePolice(null);

        // Create the CongePolice, which fails.
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);


        restCongePoliceMockMvc.perform(post("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isBadRequest());

        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCongePolices() throws Exception {
        // Initialize the database
        congePoliceRepository.saveAndFlush(congePolice);

        // Get all the congePoliceList
        restCongePoliceMockMvc.perform(get("/api/conge-polices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(congePolice.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberCongePolice").value(hasItem(DEFAULT_NUMBER_CONGE_POLICE)))
            .andExpect(jsonPath("$.[*].endDateCongePolice").value(hasItem(DEFAULT_END_DATE_CONGE_POLICE.toString())))
            .andExpect(jsonPath("$.[*].validCongePolice").value(hasItem(DEFAULT_VALID_CONGE_POLICE.booleanValue())))
            .andExpect(jsonPath("$.[*].descCongePolice").value(hasItem(DEFAULT_DESC_CONGE_POLICE)));
    }
    
    @Test
    @Transactional
    public void getCongePolice() throws Exception {
        // Initialize the database
        congePoliceRepository.saveAndFlush(congePolice);

        // Get the congePolice
        restCongePoliceMockMvc.perform(get("/api/conge-polices/{id}", congePolice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(congePolice.getId().intValue()))
            .andExpect(jsonPath("$.numberCongePolice").value(DEFAULT_NUMBER_CONGE_POLICE))
            .andExpect(jsonPath("$.endDateCongePolice").value(DEFAULT_END_DATE_CONGE_POLICE.toString()))
            .andExpect(jsonPath("$.validCongePolice").value(DEFAULT_VALID_CONGE_POLICE.booleanValue()))
            .andExpect(jsonPath("$.descCongePolice").value(DEFAULT_DESC_CONGE_POLICE));
    }
    @Test
    @Transactional
    public void getNonExistingCongePolice() throws Exception {
        // Get the congePolice
        restCongePoliceMockMvc.perform(get("/api/conge-polices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCongePolice() throws Exception {
        // Initialize the database
        congePoliceRepository.saveAndFlush(congePolice);

        int databaseSizeBeforeUpdate = congePoliceRepository.findAll().size();

        // Update the congePolice
        CongePolice updatedCongePolice = congePoliceRepository.findById(congePolice.getId()).get();
        // Disconnect from session so that the updates on updatedCongePolice are not directly saved in db
        em.detach(updatedCongePolice);
        updatedCongePolice
            .numberCongePolice(UPDATED_NUMBER_CONGE_POLICE)
            .endDateCongePolice(UPDATED_END_DATE_CONGE_POLICE)
            .validCongePolice(UPDATED_VALID_CONGE_POLICE)
            .descCongePolice(UPDATED_DESC_CONGE_POLICE);
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(updatedCongePolice);

        restCongePoliceMockMvc.perform(put("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isOk());

        // Validate the CongePolice in the database
        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeUpdate);
        CongePolice testCongePolice = congePoliceList.get(congePoliceList.size() - 1);
        assertThat(testCongePolice.getNumberCongePolice()).isEqualTo(UPDATED_NUMBER_CONGE_POLICE);
        assertThat(testCongePolice.getEndDateCongePolice()).isEqualTo(UPDATED_END_DATE_CONGE_POLICE);
        assertThat(testCongePolice.isValidCongePolice()).isEqualTo(UPDATED_VALID_CONGE_POLICE);
        assertThat(testCongePolice.getDescCongePolice()).isEqualTo(UPDATED_DESC_CONGE_POLICE);

        // Validate the CongePolice in Elasticsearch
        verify(mockCongePoliceSearchRepository, times(1)).save(testCongePolice);
    }

    @Test
    @Transactional
    public void updateNonExistingCongePolice() throws Exception {
        int databaseSizeBeforeUpdate = congePoliceRepository.findAll().size();

        // Create the CongePolice
        CongePoliceDTO congePoliceDTO = congePoliceMapper.toDto(congePolice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCongePoliceMockMvc.perform(put("/api/conge-polices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congePoliceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CongePolice in the database
        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CongePolice in Elasticsearch
        verify(mockCongePoliceSearchRepository, times(0)).save(congePolice);
    }

    @Test
    @Transactional
    public void deleteCongePolice() throws Exception {
        // Initialize the database
        congePoliceRepository.saveAndFlush(congePolice);

        int databaseSizeBeforeDelete = congePoliceRepository.findAll().size();

        // Delete the congePolice
        restCongePoliceMockMvc.perform(delete("/api/conge-polices/{id}", congePolice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CongePolice> congePoliceList = congePoliceRepository.findAll();
        assertThat(congePoliceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CongePolice in Elasticsearch
        verify(mockCongePoliceSearchRepository, times(1)).deleteById(congePolice.getId());
    }

    @Test
    @Transactional
    public void searchCongePolice() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        congePoliceRepository.saveAndFlush(congePolice);
        when(mockCongePoliceSearchRepository.search(queryStringQuery("id:" + congePolice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(congePolice), PageRequest.of(0, 1), 1));

        // Search the congePolice
        restCongePoliceMockMvc.perform(get("/api/_search/conge-polices?query=id:" + congePolice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(congePolice.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberCongePolice").value(hasItem(DEFAULT_NUMBER_CONGE_POLICE)))
            .andExpect(jsonPath("$.[*].endDateCongePolice").value(hasItem(DEFAULT_END_DATE_CONGE_POLICE.toString())))
            .andExpect(jsonPath("$.[*].validCongePolice").value(hasItem(DEFAULT_VALID_CONGE_POLICE.booleanValue())))
            .andExpect(jsonPath("$.[*].descCongePolice").value(hasItem(DEFAULT_DESC_CONGE_POLICE)));
    }
}
