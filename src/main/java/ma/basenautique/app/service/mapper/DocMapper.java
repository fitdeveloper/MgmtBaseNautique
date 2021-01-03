package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.DocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doc} and its DTO {@link DocDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContentDocMapper.class, TypeDocMapper.class, MembershipMapper.class})
public interface DocMapper extends EntityMapper<DocDTO, Doc> {

    @Mapping(source = "contentDoc.id", target = "contentDocId")
    @Mapping(source = "typeDoc.id", target = "typeDocId")
    @Mapping(source = "membership.id", target = "membershipId")
    DocDTO toDto(Doc doc);

    @Mapping(source = "contentDocId", target = "contentDoc")
    @Mapping(source = "typeDocId", target = "typeDoc")
    @Mapping(target = "insurance", ignore = true)
    @Mapping(target = "congePolice", ignore = true)
    @Mapping(target = "medicalCertificate", ignore = true)
    @Mapping(target = "dealership", ignore = true)
    @Mapping(source = "membershipId", target = "membership")
    Doc toEntity(DocDTO docDTO);

    default Doc fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doc doc = new Doc();
        doc.setId(id);
        return doc;
    }
}
