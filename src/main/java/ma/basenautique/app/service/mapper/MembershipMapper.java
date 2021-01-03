package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.MembershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Membership} and its DTO {@link MembershipDTO}.
 */
@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface MembershipMapper extends EntityMapper<MembershipDTO, Membership> {

    @Mapping(source = "member.id", target = "memberId")
    MembershipDTO toDto(Membership membership);

    @Mapping(target = "guardings", ignore = true)
    @Mapping(target = "removeGuarding", ignore = true)
    @Mapping(target = "docs", ignore = true)
    @Mapping(target = "removeDoc", ignore = true)
    @Mapping(target = "medicalCertificates", ignore = true)
    @Mapping(target = "removeMedicalCertificate", ignore = true)
    @Mapping(target = "dealerships", ignore = true)
    @Mapping(target = "removeDealership", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "removeVehicle", ignore = true)
    @Mapping(source = "memberId", target = "member")
    Membership toEntity(MembershipDTO membershipDTO);

    default Membership fromId(Long id) {
        if (id == null) {
            return null;
        }
        Membership membership = new Membership();
        membership.setId(id);
        return membership;
    }
}
