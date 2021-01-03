package ma.basenautique.app.web.rest;

import ma.basenautique.app.MgmtBaseNautiqueApp;
import ma.basenautique.app.domain.Member;
import ma.basenautique.app.repository.MemberRepository;
import ma.basenautique.app.repository.search.MemberSearchRepository;
import ma.basenautique.app.service.MemberService;
import ma.basenautique.app.service.dto.MemberDTO;
import ma.basenautique.app.service.mapper.MemberMapper;

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

import ma.basenautique.app.domain.enumeration.MemberType;
/**
 * Integration tests for the {@link MemberResource} REST controller.
 */
@SpringBootTest(classes = MgmtBaseNautiqueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MemberResourceIT {

    private static final String DEFAULT_NUMBER_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_MEMBER = "BBBBBBBBBB";

    private static final MemberType DEFAULT_TYPE_MEMBER = MemberType.MEMBER_INDIVIDUAL;
    private static final MemberType UPDATED_TYPE_MEMBER = MemberType.MEMBER_VEHICLEOWNER;

    private static final String DEFAULT_CIN_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_CIN_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_MEMBER = "WzLgrR@N8yrW.mow.WM";
    private static final String UPDATED_EMAIL_MEMBER = "Irr4TD@p.2Oel.IYwbv3.Jt";

    private static final String DEFAULT_NUMBER_PHONE_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_PHONE_MEMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB_MEMBER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB_MEMBER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADRESS_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_MEMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_MEMBER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_MEMBER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_MEMBER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_MEMBER_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESC_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_DESC_MEMBER = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    /**
     * This repository is mocked in the ma.basenautique.app.repository.search test package.
     *
     * @see ma.basenautique.app.repository.search.MemberSearchRepositoryMockConfiguration
     */
    @Autowired
    private MemberSearchRepository mockMemberSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .numberMember(DEFAULT_NUMBER_MEMBER)
            .typeMember(DEFAULT_TYPE_MEMBER)
            .cinMember(DEFAULT_CIN_MEMBER)
            .firstnameMember(DEFAULT_FIRSTNAME_MEMBER)
            .lastnameMember(DEFAULT_LASTNAME_MEMBER)
            .emailMember(DEFAULT_EMAIL_MEMBER)
            .numberPhoneMember(DEFAULT_NUMBER_PHONE_MEMBER)
            .dobMember(DEFAULT_DOB_MEMBER)
            .adressMember(DEFAULT_ADRESS_MEMBER)
            .imageMember(DEFAULT_IMAGE_MEMBER)
            .imageMemberContentType(DEFAULT_IMAGE_MEMBER_CONTENT_TYPE)
            .descMember(DEFAULT_DESC_MEMBER);
        return member;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member()
            .numberMember(UPDATED_NUMBER_MEMBER)
            .typeMember(UPDATED_TYPE_MEMBER)
            .cinMember(UPDATED_CIN_MEMBER)
            .firstnameMember(UPDATED_FIRSTNAME_MEMBER)
            .lastnameMember(UPDATED_LASTNAME_MEMBER)
            .emailMember(UPDATED_EMAIL_MEMBER)
            .numberPhoneMember(UPDATED_NUMBER_PHONE_MEMBER)
            .dobMember(UPDATED_DOB_MEMBER)
            .adressMember(UPDATED_ADRESS_MEMBER)
            .imageMember(UPDATED_IMAGE_MEMBER)
            .imageMemberContentType(UPDATED_IMAGE_MEMBER_CONTENT_TYPE)
            .descMember(UPDATED_DESC_MEMBER);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getNumberMember()).isEqualTo(DEFAULT_NUMBER_MEMBER);
        assertThat(testMember.getTypeMember()).isEqualTo(DEFAULT_TYPE_MEMBER);
        assertThat(testMember.getCinMember()).isEqualTo(DEFAULT_CIN_MEMBER);
        assertThat(testMember.getFirstnameMember()).isEqualTo(DEFAULT_FIRSTNAME_MEMBER);
        assertThat(testMember.getLastnameMember()).isEqualTo(DEFAULT_LASTNAME_MEMBER);
        assertThat(testMember.getEmailMember()).isEqualTo(DEFAULT_EMAIL_MEMBER);
        assertThat(testMember.getNumberPhoneMember()).isEqualTo(DEFAULT_NUMBER_PHONE_MEMBER);
        assertThat(testMember.getDobMember()).isEqualTo(DEFAULT_DOB_MEMBER);
        assertThat(testMember.getAdressMember()).isEqualTo(DEFAULT_ADRESS_MEMBER);
        assertThat(testMember.getImageMember()).isEqualTo(DEFAULT_IMAGE_MEMBER);
        assertThat(testMember.getImageMemberContentType()).isEqualTo(DEFAULT_IMAGE_MEMBER_CONTENT_TYPE);
        assertThat(testMember.getDescMember()).isEqualTo(DEFAULT_DESC_MEMBER);

        // Validate the Member in Elasticsearch
        verify(mockMemberSearchRepository, times(1)).save(testMember);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);

        // Validate the Member in Elasticsearch
        verify(mockMemberSearchRepository, times(0)).save(member);
    }


    @Test
    @Transactional
    public void checkNumberMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setNumberMember(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);


        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setTypeMember(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);


        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCinMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setCinMember(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);


        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setEmailMember(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);


        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setDobMember(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);


        restMemberMockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMember").value(hasItem(DEFAULT_NUMBER_MEMBER)))
            .andExpect(jsonPath("$.[*].typeMember").value(hasItem(DEFAULT_TYPE_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].cinMember").value(hasItem(DEFAULT_CIN_MEMBER)))
            .andExpect(jsonPath("$.[*].firstnameMember").value(hasItem(DEFAULT_FIRSTNAME_MEMBER)))
            .andExpect(jsonPath("$.[*].lastnameMember").value(hasItem(DEFAULT_LASTNAME_MEMBER)))
            .andExpect(jsonPath("$.[*].emailMember").value(hasItem(DEFAULT_EMAIL_MEMBER)))
            .andExpect(jsonPath("$.[*].numberPhoneMember").value(hasItem(DEFAULT_NUMBER_PHONE_MEMBER)))
            .andExpect(jsonPath("$.[*].dobMember").value(hasItem(DEFAULT_DOB_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].adressMember").value(hasItem(DEFAULT_ADRESS_MEMBER)))
            .andExpect(jsonPath("$.[*].imageMemberContentType").value(hasItem(DEFAULT_IMAGE_MEMBER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageMember").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_MEMBER))))
            .andExpect(jsonPath("$.[*].descMember").value(hasItem(DEFAULT_DESC_MEMBER)));
    }
    
    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.numberMember").value(DEFAULT_NUMBER_MEMBER))
            .andExpect(jsonPath("$.typeMember").value(DEFAULT_TYPE_MEMBER.toString()))
            .andExpect(jsonPath("$.cinMember").value(DEFAULT_CIN_MEMBER))
            .andExpect(jsonPath("$.firstnameMember").value(DEFAULT_FIRSTNAME_MEMBER))
            .andExpect(jsonPath("$.lastnameMember").value(DEFAULT_LASTNAME_MEMBER))
            .andExpect(jsonPath("$.emailMember").value(DEFAULT_EMAIL_MEMBER))
            .andExpect(jsonPath("$.numberPhoneMember").value(DEFAULT_NUMBER_PHONE_MEMBER))
            .andExpect(jsonPath("$.dobMember").value(DEFAULT_DOB_MEMBER.toString()))
            .andExpect(jsonPath("$.adressMember").value(DEFAULT_ADRESS_MEMBER))
            .andExpect(jsonPath("$.imageMemberContentType").value(DEFAULT_IMAGE_MEMBER_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageMember").value(Base64Utils.encodeToString(DEFAULT_IMAGE_MEMBER)))
            .andExpect(jsonPath("$.descMember").value(DEFAULT_DESC_MEMBER));
    }
    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .numberMember(UPDATED_NUMBER_MEMBER)
            .typeMember(UPDATED_TYPE_MEMBER)
            .cinMember(UPDATED_CIN_MEMBER)
            .firstnameMember(UPDATED_FIRSTNAME_MEMBER)
            .lastnameMember(UPDATED_LASTNAME_MEMBER)
            .emailMember(UPDATED_EMAIL_MEMBER)
            .numberPhoneMember(UPDATED_NUMBER_PHONE_MEMBER)
            .dobMember(UPDATED_DOB_MEMBER)
            .adressMember(UPDATED_ADRESS_MEMBER)
            .imageMember(UPDATED_IMAGE_MEMBER)
            .imageMemberContentType(UPDATED_IMAGE_MEMBER_CONTENT_TYPE)
            .descMember(UPDATED_DESC_MEMBER);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getNumberMember()).isEqualTo(UPDATED_NUMBER_MEMBER);
        assertThat(testMember.getTypeMember()).isEqualTo(UPDATED_TYPE_MEMBER);
        assertThat(testMember.getCinMember()).isEqualTo(UPDATED_CIN_MEMBER);
        assertThat(testMember.getFirstnameMember()).isEqualTo(UPDATED_FIRSTNAME_MEMBER);
        assertThat(testMember.getLastnameMember()).isEqualTo(UPDATED_LASTNAME_MEMBER);
        assertThat(testMember.getEmailMember()).isEqualTo(UPDATED_EMAIL_MEMBER);
        assertThat(testMember.getNumberPhoneMember()).isEqualTo(UPDATED_NUMBER_PHONE_MEMBER);
        assertThat(testMember.getDobMember()).isEqualTo(UPDATED_DOB_MEMBER);
        assertThat(testMember.getAdressMember()).isEqualTo(UPDATED_ADRESS_MEMBER);
        assertThat(testMember.getImageMember()).isEqualTo(UPDATED_IMAGE_MEMBER);
        assertThat(testMember.getImageMemberContentType()).isEqualTo(UPDATED_IMAGE_MEMBER_CONTENT_TYPE);
        assertThat(testMember.getDescMember()).isEqualTo(UPDATED_DESC_MEMBER);

        // Validate the Member in Elasticsearch
        verify(mockMemberSearchRepository, times(1)).save(testMember);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc.perform(put("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Member in Elasticsearch
        verify(mockMemberSearchRepository, times(0)).save(member);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Member in Elasticsearch
        verify(mockMemberSearchRepository, times(1)).deleteById(member.getId());
    }

    @Test
    @Transactional
    public void searchMember() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        memberRepository.saveAndFlush(member);
        when(mockMemberSearchRepository.search(queryStringQuery("id:" + member.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(member), PageRequest.of(0, 1), 1));

        // Search the member
        restMemberMockMvc.perform(get("/api/_search/members?query=id:" + member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberMember").value(hasItem(DEFAULT_NUMBER_MEMBER)))
            .andExpect(jsonPath("$.[*].typeMember").value(hasItem(DEFAULT_TYPE_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].cinMember").value(hasItem(DEFAULT_CIN_MEMBER)))
            .andExpect(jsonPath("$.[*].firstnameMember").value(hasItem(DEFAULT_FIRSTNAME_MEMBER)))
            .andExpect(jsonPath("$.[*].lastnameMember").value(hasItem(DEFAULT_LASTNAME_MEMBER)))
            .andExpect(jsonPath("$.[*].emailMember").value(hasItem(DEFAULT_EMAIL_MEMBER)))
            .andExpect(jsonPath("$.[*].numberPhoneMember").value(hasItem(DEFAULT_NUMBER_PHONE_MEMBER)))
            .andExpect(jsonPath("$.[*].dobMember").value(hasItem(DEFAULT_DOB_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].adressMember").value(hasItem(DEFAULT_ADRESS_MEMBER)))
            .andExpect(jsonPath("$.[*].imageMemberContentType").value(hasItem(DEFAULT_IMAGE_MEMBER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageMember").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_MEMBER))))
            .andExpect(jsonPath("$.[*].descMember").value(hasItem(DEFAULT_DESC_MEMBER)));
    }
}
