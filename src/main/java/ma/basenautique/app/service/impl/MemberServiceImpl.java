package ma.basenautique.app.service.impl;

import ma.basenautique.app.service.MemberService;
import ma.basenautique.app.domain.Member;
import ma.basenautique.app.repository.MemberRepository;
import ma.basenautique.app.repository.search.MemberSearchRepository;
import ma.basenautique.app.service.dto.MemberDTO;
import ma.basenautique.app.service.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Member}.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    private final MemberSearchRepository memberSearchRepository;

    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper, MemberSearchRepository memberSearchRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.memberSearchRepository = memberSearchRepository;
    }

    @Override
    public MemberDTO save(MemberDTO memberDTO) {
        log.debug("Request to save Member : {}", memberDTO);
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        MemberDTO result = memberMapper.toDto(member);
        memberSearchRepository.save(member);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return memberRepository.findAll(pageable)
            .map(memberMapper::toDto);
    }



    /**
     *  Get all the members where Child is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MemberDTO> findAllWhereChildIsNull() {
        log.debug("Request to get all members where Child is null");
        return StreamSupport
            .stream(memberRepository.findAll().spliterator(), false)
            .filter(member -> member.getChild() == null)
            .map(memberMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MemberDTO> findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return memberRepository.findById(id)
            .map(memberMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.deleteById(id);
        memberSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Members for query {}", query);
        return memberSearchRepository.search(queryStringQuery(query), pageable)
            .map(memberMapper::toDto);
    }
}
