package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring", uses = {MembershipMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "membership.id", target = "membershipId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(target = "congePolices", ignore = true)
    @Mapping(target = "removeCongePolice", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "removeInsurance", ignore = true)
    @Mapping(source = "membershipId", target = "membership")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
