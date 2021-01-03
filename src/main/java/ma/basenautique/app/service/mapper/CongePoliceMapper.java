package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.CongePoliceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CongePolice} and its DTO {@link CongePoliceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocMapper.class, VehicleMapper.class})
public interface CongePoliceMapper extends EntityMapper<CongePoliceDTO, CongePolice> {

    @Mapping(source = "doc.id", target = "docId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    CongePoliceDTO toDto(CongePolice congePolice);

    @Mapping(source = "docId", target = "doc")
    @Mapping(source = "vehicleId", target = "vehicle")
    CongePolice toEntity(CongePoliceDTO congePoliceDTO);

    default CongePolice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CongePolice congePolice = new CongePolice();
        congePolice.setId(id);
        return congePolice;
    }
}
