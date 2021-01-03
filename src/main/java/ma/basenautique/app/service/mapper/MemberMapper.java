package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.MemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssociationMapper.class})
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "association.id", target = "associationId")
    MemberDTO toDto(Member member);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "removeMembership", ignore = true)
    @Mapping(target = "child", ignore = true)
    @Mapping(source = "associationId", target = "association")
    Member toEntity(MemberDTO memberDTO);

    default Member fromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
