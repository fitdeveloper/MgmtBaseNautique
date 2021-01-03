package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.MembershipService;
import ma.basenautique.app.domain.Membership;
import ma.basenautique.app.repository.MembershipRepository;
import ma.basenautique.app.repository.search.MembershipSearchRepository;
import ma.basenautique.app.service.dto.MembershipDTO;
import ma.basenautique.app.service.mapper.MembershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Membership}.
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private final MembershipRepository membershipRepository;

    private final MembershipMapper membershipMapper;

    private final MembershipSearchRepository membershipSearchRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository, MembershipMapper membershipMapper, MembershipSearchRepository membershipSearchRepository) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
        this.membershipSearchRepository = membershipSearchRepository;
    }

    @Override
    public MembershipDTO save(MembershipDTO membershipDTO) {
        log.debug("Request to save Membership : {}", membershipDTO);
        Membership membership = membershipMapper.toEntity(membershipDTO);
        membership = membershipRepository.save(membership);
        MembershipDTO result = membershipMapper.toDto(membership);
        membershipSearchRepository.save(membership);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll(pageable)
            .map(membershipMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipDTO> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id)
            .map(membershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
        membershipSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Memberships for query {}", query);
        return membershipSearchRepository.search(queryStringQuery(query), pageable)
            .map(membershipMapper::toDto);
    }
}
