package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.GuardingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guarding} and its DTO {@link GuardingDTO}.
 */
@Mapper(componentModel = "spring", uses = {MembershipMapper.class})
public interface GuardingMapper extends EntityMapper<GuardingDTO, Guarding> {

    @Mapping(source = "membership.id", target = "membershipId")
    GuardingDTO toDto(Guarding guarding);

    @Mapping(source = "membershipId", target = "membership")
    Guarding toEntity(GuardingDTO guardingDTO);

    default Guarding fromId(Long id) {
        if (id == null) {
            return null;
        }
        Guarding guarding = new Guarding();
        guarding.setId(id);
        return guarding;
    }
}
