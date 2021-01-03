package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.DealershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dealership} and its DTO {@link DealershipDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocMapper.class, MembershipMapper.class})
public interface DealershipMapper extends EntityMapper<DealershipDTO, Dealership> {

    @Mapping(source = "doc.id", target = "docId")
    @Mapping(source = "membership.id", target = "membershipId")
    DealershipDTO toDto(Dealership dealership);

    @Mapping(source = "docId", target = "doc")
    @Mapping(source = "membershipId", target = "membership")
    Dealership toEntity(DealershipDTO dealershipDTO);

    default Dealership fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dealership dealership = new Dealership();
        dealership.setId(id);
        return dealership;
    }
}
