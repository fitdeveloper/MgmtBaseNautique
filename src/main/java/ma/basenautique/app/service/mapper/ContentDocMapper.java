package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.ContentDocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContentDoc} and its DTO {@link ContentDocDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentDocMapper extends EntityMapper<ContentDocDTO, ContentDoc> {


    @Mapping(target = "doc", ignore = true)
    ContentDoc toEntity(ContentDocDTO contentDocDTO);

    default ContentDoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentDoc contentDoc = new ContentDoc();
        contentDoc.setId(id);
        return contentDoc;
    }
}
