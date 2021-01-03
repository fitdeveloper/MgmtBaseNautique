package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.AssociationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Association} and its DTO {@link AssociationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {


    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMember", ignore = true)
    Association toEntity(AssociationDTO associationDTO);

    default Association fromId(Long id) {
        if (id == null) {
            return null;
        }
        Association association = new Association();
        association.setId(id);
        return association;
    }
}
