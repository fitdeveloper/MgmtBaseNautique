package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Membership;
import ma.basenautique.app.repository.MembershipRepository;
import ma.basenautique.app.repository.search.MembershipSearchRepository;
import ma.basenautique.app.service.MembershipService;
import ma.basenautique.app.service.dto.MembershipDTO;
import ma.basenautique.app.service.mapper.MembershipMapper;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@link MembershipResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembershipResourceIT {

    private static final String DEFAULT_NUMBER_MEMBERSHIP = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_MEMBERSHIP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_MEMBERSHIP = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_MEMBERSHIP = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE_MEMBERSHIP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_MEMBERSHIP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE_MEMBERSHIP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_MEMBERSHIP = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALID_MEMBERSHIP = false;
    private static final Boolean UPDATED_VALID_MEMBERSHIP = true;

    private static final String DEFAULT_DESC_MEMBERSHIP = "AAAAAAAAAA";
    private static final String UPDATED_DESC_MEMBERSHIP = "BBBBBBBBBB";

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private MembershipService membershipService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.MembershipSearchRepositoryMockConfiguration
     */
    @Autowired
    private MembershipSearchRepository mockMembershipSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipMockMvc;

    private Membership membership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createEntity(EntityManager em) {
        Membership membership = new Membership()
            .numberMembership(DEFAULT_NUMBER_MEMBERSHIP)
            .amountMembership(DEFAULT_AMOUNT_MEMBERSHIP)
            .startDateMembership(DEFAULT_START_DATE_MEMBERSHIP)
            .endDateMembership(DEFAULT_END_DATE_MEMBERSHIP)
            .validMembership(DEFAULT_VALID_MEMBERSHIP)
            .descMembership(DEFAULT_DESC_MEMBERSHIP);
        return membership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createUpdatedEntity(EntityManager em) {
        Membership membership = new Membership()
            .numberMembership(UPDATED_NUMBER_MEMBERSHIP)
            .amountMembership(UPDATED_AMOUNT_MEMBERSHIP)
            .startDateMembership(UPDATED_START_DATE_MEMBERSHIP)
            .endDateMembership(UPDATED_END_DATE_MEMBERSHIP)
            .validMembership(UPDATED_VALID_MEMBERSHIP)
            .descMembership(UPDATED_DESC_MEMBERSHIP);
        return membership;
    }

    @BeforeEach
    public void initTest() {
        membership = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembership() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();
        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isCreated());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate + 1);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getNumberMembership()).isEqualTo(DEFAULT_NUMBER_MEMBERSHIP);
        assertThat(testMembership.getAmountMembership()).isEqualTo(DEFAULT_AMOUNT_MEMBERSHIP);
        assertThat(testMembership.getStartDateMembership()).isEqualTo(DEFAULT_START_DATE_MEMBERSHIP);
        assertThat(testMembership.getEndDateMembership()).isEqualTo(DEFAULT_END_DATE_MEMBERSHIP);
        assertThat(testMembership.isValidMembership()).isEqualTo(DEFAULT_VALID_MEMBERSHIP);
        assertThat(testMembership.getDescMembership()).isEqualTo(DEFAULT_DESC_MEMBERSHIP);

        // Validate the Membership in Elasticsearch
        verify(mockMembershipSearchRepository, times(1)).save(testMembership);
    }

    @Test
    @Transactional
    public void createMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();

        // Create the Membership with an existing ID
        membership.setId(1L);
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate);

        // Validate the Membership in Elasticsearch
        verify(mockMembershipSearchRepository, times(0)).save(membership);
    }


    @Test
    @Transactional
    public void checkNumberMembershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setNumberMembership(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);


        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateMembershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setStartDateMembership(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);


        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateMembershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setEndDateMembership(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);


        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidMembershipIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setValidMembership(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);


        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemberships() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get all the membershipList
        restMembershipMockMvc.perform(get("/api/memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membership.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMembership").value(hasItem(DEFAULT_NUMBER_MEMBERSHIP)))
            .andExpect(jsonPath("$.[*].amountMembership").value(hasItem(DEFAULT_AMOUNT_MEMBERSHIP.intValue())))
            .andExpect(jsonPath("$.[*].startDateMembership").value(hasItem(DEFAULT_START_DATE_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].endDateMembership").value(hasItem(DEFAULT_END_DATE_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].validMembership").value(hasItem(DEFAULT_VALID_MEMBERSHIP.booleanValue())))
            .andExpect(jsonPath("$.[*].descMembership").value(hasItem(DEFAULT_DESC_MEMBERSHIP)));
    }
    
    @Test
    @Transactional
    public void getMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", membership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membership.getId().intValue()))
            .andExpect(jsonPath("$.numberMembership").value(DEFAULT_NUMBER_MEMBERSHIP))
            .andExpect(jsonPath("$.amountMembership").value(DEFAULT_AMOUNT_MEMBERSHIP.intValue()))
            .andExpect(jsonPath("$.startDateMembership").value(DEFAULT_START_DATE_MEMBERSHIP.toString()))
            .andExpect(jsonPath("$.endDateMembership").value(DEFAULT_END_DATE_MEMBERSHIP.toString()))
            .andExpect(jsonPath("$.validMembership").value(DEFAULT_VALID_MEMBERSHIP.booleanValue()))
            .andExpect(jsonPath("$.descMembership").value(DEFAULT_DESC_MEMBERSHIP));
    }
    @Test
    @Transactional
    public void getNonExistingMembership() throws Exception {
        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership
        Membership updatedMembership = membershipRepository.findById(membership.getId()).get();
        // Disconnect from session so that the updates on updatedMembership are not directly saved in db
        em.detach(updatedMembership);
        updatedMembership
            .numberMembership(UPDATED_NUMBER_MEMBERSHIP)
            .amountMembership(UPDATED_AMOUNT_MEMBERSHIP)
            .startDateMembership(UPDATED_START_DATE_MEMBERSHIP)
            .endDateMembership(UPDATED_END_DATE_MEMBERSHIP)
            .validMembership(UPDATED_VALID_MEMBERSHIP)
            .descMembership(UPDATED_DESC_MEMBERSHIP);
        MembershipDTO membershipDTO = membershipMapper.toDto(updatedMembership);

        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getNumberMembership()).isEqualTo(UPDATED_NUMBER_MEMBERSHIP);
        assertThat(testMembership.getAmountMembership()).isEqualTo(UPDATED_AMOUNT_MEMBERSHIP);
        assertThat(testMembership.getStartDateMembership()).isEqualTo(UPDATED_START_DATE_MEMBERSHIP);
        assertThat(testMembership.getEndDateMembership()).isEqualTo(UPDATED_END_DATE_MEMBERSHIP);
        assertThat(testMembership.isValidMembership()).isEqualTo(UPDATED_VALID_MEMBERSHIP);
        assertThat(testMembership.getDescMembership()).isEqualTo(UPDATED_DESC_MEMBERSHIP);

        // Validate the Membership in Elasticsearch
        verify(mockMembershipSearchRepository, times(1)).save(testMembership);
    }

    @Test
    @Transactional
    public void updateNonExistingMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Membership in Elasticsearch
        verify(mockMembershipSearchRepository, times(0)).save(membership);
    }

    @Test
    @Transactional
    public void deleteMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeDelete = membershipRepository.findAll().size();

        // Delete the membership
        restMembershipMockMvc.perform(delete("/api/memberships/{id}", membership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Membership in Elasticsearch
        verify(mockMembershipSearchRepository, times(1)).deleteById(membership.getId());
    }

    @Test
    @Transactional
    public void searchMembership() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        membershipRepository.saveAndFlush(membership);
        when(mockMembershipSearchRepository.search(queryStringQuery("id:" + membership.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(membership), PageRequest.of(0, 1), 1));

        // Search the membership
        restMembershipMockMvc.perform(get("/api/_search/memberships?query=id:" + membership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membership.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMembership").value(hasItem(DEFAULT_NUMBER_MEMBERSHIP)))
            .andExpect(jsonPath("$.[*].amountMembership").value(hasItem(DEFAULT_AMOUNT_MEMBERSHIP.intValue())))
            .andExpect(jsonPath("$.[*].startDateMembership").value(hasItem(DEFAULT_START_DATE_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].endDateMembership").value(hasItem(DEFAULT_END_DATE_MEMBERSHIP.toString())))
            .andExpect(jsonPath("$.[*].validMembership").value(hasItem(DEFAULT_VALID_MEMBERSHIP.booleanValue())))
            .andExpect(jsonPath("$.[*].descMembership").value(hasItem(DEFAULT_DESC_MEMBERSHIP)));
    }
}
