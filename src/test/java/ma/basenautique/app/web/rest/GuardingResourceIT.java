package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Guarding;
import ma.basenautique.app.repository.GuardingRepository;
import ma.basenautique.app.repository.search.GuardingSearchRepository;
import ma.basenautique.app.service.GuardingService;
import ma.basenautique.app.service.dto.GuardingDTO;
import ma.basenautique.app.service.mapper.GuardingMapper;

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
 * Integration tests for the {@link GuardingResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GuardingResourceIT {

    private static final String DEFAULT_NUMBER_GUARDING = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_GUARDING = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_GUARDING = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_GUARDING = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_GUARDING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_GUARDING = "BBBBBBBBBB";

    @Autowired
    private GuardingRepository guardingRepository;

    @Autowired
    private GuardingMapper guardingMapper;

    @Autowired
    private GuardingService guardingService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.GuardingSearchRepositoryMockConfiguration
     */
    @Autowired
    private GuardingSearchRepository mockGuardingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuardingMockMvc;

    private Guarding guarding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarding createEntity(EntityManager em) {
        Guarding guarding = new Guarding()
            .numberGuarding(DEFAULT_NUMBER_GUARDING)
            .titleGuarding(DEFAULT_TITLE_GUARDING)
            .descGuarding(DEFAULT_DESC_GUARDING);
        return guarding;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarding createUpdatedEntity(EntityManager em) {
        Guarding guarding = new Guarding()
            .numberGuarding(UPDATED_NUMBER_GUARDING)
            .titleGuarding(UPDATED_TITLE_GUARDING)
            .descGuarding(UPDATED_DESC_GUARDING);
        return guarding;
    }

    @BeforeEach
    public void initTest() {
        guarding = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuarding() throws Exception {
        int databaseSizeBeforeCreate = guardingRepository.findAll().size();
        // Create the Guarding
        GuardingDTO guardingDTO = guardingMapper.toDto(guarding);
        restGuardingMockMvc.perform(post("/api/guardings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardingDTO)))
            .andExpect(status().isCreated());

        // Validate the Guarding in the database
        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeCreate + 1);
        Guarding testGuarding = guardingList.get(guardingList.size() - 1);
        assertThat(testGuarding.getNumberGuarding()).isEqualTo(DEFAULT_NUMBER_GUARDING);
        assertThat(testGuarding.getTitleGuarding()).isEqualTo(DEFAULT_TITLE_GUARDING);
        assertThat(testGuarding.getDescGuarding()).isEqualTo(DEFAULT_DESC_GUARDING);

        // Validate the Guarding in Elasticsearch
        verify(mockGuardingSearchRepository, times(1)).save(testGuarding);
    }

    @Test
    @Transactional
    public void createGuardingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guardingRepository.findAll().size();

        // Create the Guarding with an existing ID
        guarding.setId(1L);
        GuardingDTO guardingDTO = guardingMapper.toDto(guarding);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuardingMockMvc.perform(post("/api/guardings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guarding in the database
        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Guarding in Elasticsearch
        verify(mockGuardingSearchRepository, times(0)).save(guarding);
    }


    @Test
    @Transactional
    public void checkNumberGuardingIsRequired() throws Exception {
        int databaseSizeBeforeTest = guardingRepository.findAll().size();
        // set the field null
        guarding.setNumberGuarding(null);

        // Create the Guarding, which fails.
        GuardingDTO guardingDTO = guardingMapper.toDto(guarding);


        restGuardingMockMvc.perform(post("/api/guardings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardingDTO)))
            .andExpect(status().isBadRequest());

        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuardings() throws Exception {
        // Initialize the database
        guardingRepository.saveAndFlush(guarding);

        // Get all the guardingList
        restGuardingMockMvc.perform(get("/api/guardings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guarding.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberGuarding").value(hasItem(DEFAULT_NUMBER_GUARDING)))
            .andExpect(jsonPath("$.[*].titleGuarding").value(hasItem(DEFAULT_TITLE_GUARDING)))
            .andExpect(jsonPath("$.[*].descGuarding").value(hasItem(DEFAULT_DESC_GUARDING)));
    }
    
    @Test
    @Transactional
    public void getGuarding() throws Exception {
        // Initialize the database
        guardingRepository.saveAndFlush(guarding);

        // Get the guarding
        restGuardingMockMvc.perform(get("/api/guardings/{id}", guarding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guarding.getId().intValue()))
            .andExpect(jsonPath("$.numberGuarding").value(DEFAULT_NUMBER_GUARDING))
            .andExpect(jsonPath("$.titleGuarding").value(DEFAULT_TITLE_GUARDING))
            .andExpect(jsonPath("$.descGuarding").value(DEFAULT_DESC_GUARDING));
    }
    @Test
    @Transactional
    public void getNonExistingGuarding() throws Exception {
        // Get the guarding
        restGuardingMockMvc.perform(get("/api/guardings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuarding() throws Exception {
        // Initialize the database
        guardingRepository.saveAndFlush(guarding);

        int databaseSizeBeforeUpdate = guardingRepository.findAll().size();

        // Update the guarding
        Guarding updatedGuarding = guardingRepository.findById(guarding.getId()).get();
        // Disconnect from session so that the updates on updatedGuarding are not directly saved in db
        em.detach(updatedGuarding);
        updatedGuarding
            .numberGuarding(UPDATED_NUMBER_GUARDING)
            .titleGuarding(UPDATED_TITLE_GUARDING)
            .descGuarding(UPDATED_DESC_GUARDING);
        GuardingDTO guardingDTO = guardingMapper.toDto(updatedGuarding);

        restGuardingMockMvc.perform(put("/api/guardings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardingDTO)))
            .andExpect(status().isOk());

        // Validate the Guarding in the database
        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeUpdate);
        Guarding testGuarding = guardingList.get(guardingList.size() - 1);
        assertThat(testGuarding.getNumberGuarding()).isEqualTo(UPDATED_NUMBER_GUARDING);
        assertThat(testGuarding.getTitleGuarding()).isEqualTo(UPDATED_TITLE_GUARDING);
        assertThat(testGuarding.getDescGuarding()).isEqualTo(UPDATED_DESC_GUARDING);

        // Validate the Guarding in Elasticsearch
        verify(mockGuardingSearchRepository, times(1)).save(testGuarding);
    }

    @Test
    @Transactional
    public void updateNonExistingGuarding() throws Exception {
        int databaseSizeBeforeUpdate = guardingRepository.findAll().size();

        // Create the Guarding
        GuardingDTO guardingDTO = guardingMapper.toDto(guarding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuardingMockMvc.perform(put("/api/guardings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guardingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guarding in the database
        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Guarding in Elasticsearch
        verify(mockGuardingSearchRepository, times(0)).save(guarding);
    }

    @Test
    @Transactional
    public void deleteGuarding() throws Exception {
        // Initialize the database
        guardingRepository.saveAndFlush(guarding);

        int databaseSizeBeforeDelete = guardingRepository.findAll().size();

        // Delete the guarding
        restGuardingMockMvc.perform(delete("/api/guardings/{id}", guarding.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guarding> guardingList = guardingRepository.findAll();
        assertThat(guardingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Guarding in Elasticsearch
        verify(mockGuardingSearchRepository, times(1)).deleteById(guarding.getId());
    }

    @Test
    @Transactional
    public void searchGuarding() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        guardingRepository.saveAndFlush(guarding);
        when(mockGuardingSearchRepository.search(queryStringQuery("id:" + guarding.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(guarding), PageRequest.of(0, 1), 1));

        // Search the guarding
        restGuardingMockMvc.perform(get("/api/_search/guardings?query=id:" + guarding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guarding.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberGuarding").value(hasItem(DEFAULT_NUMBER_GUARDING)))
            .andExpect(jsonPath("$.[*].titleGuarding").value(hasItem(DEFAULT_TITLE_GUARDING)))
            .andExpect(jsonPath("$.[*].descGuarding").value(hasItem(DEFAULT_DESC_GUARDING)));
    }
}
