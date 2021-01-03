package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.TypeDocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeDoc} and its DTO {@link TypeDocDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeDocMapper extends EntityMapper<TypeDocDTO, TypeDoc> {


    @Mapping(target = "doc", ignore = true)
    TypeDoc toEntity(TypeDocDTO typeDocDTO);

    default TypeDoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setId(id);
        return typeDoc;
    }
}
