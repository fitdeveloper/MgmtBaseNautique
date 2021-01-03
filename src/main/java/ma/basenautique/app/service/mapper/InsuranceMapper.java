package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.InsuranceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Insurance} and its DTO {@link InsuranceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocMapper.class, VehicleMapper.class})
public interface InsuranceMapper extends EntityMapper<InsuranceDTO, Insurance> {

    @Mapping(source = "doc.id", target = "docId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    InsuranceDTO toDto(Insurance insurance);

    @Mapping(source = "docId", target = "doc")
    @Mapping(source = "vehicleId", target = "vehicle")
    Insurance toEntity(InsuranceDTO insuranceDTO);

    default Insurance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Insurance insurance = new Insurance();
        insurance.setId(id);
        return insurance;
    }
}
