package ma.basenautique.app.service.mapper;


import ma.basenautique.app.domain.*;
import ma.basenautique.app.service.dto.MedicalCertificateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalCertificate} and its DTO {@link MedicalCertificateDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocMapper.class, MembershipMapper.class})
public interface MedicalCertificateMapper extends EntityMapper<MedicalCertificateDTO, MedicalCertificate> {

    @Mapping(source = "doc.id", target = "docId")
    @Mapping(source = "membership.id", target = "membershipId")
    MedicalCertificateDTO toDto(MedicalCertificate medicalCertificate);

    @Mapping(source = "docId", target = "doc")
    @Mapping(source = "membershipId", target = "membership")
    MedicalCertificate toEntity(MedicalCertificateDTO medicalCertificateDTO);

    default MedicalCertificate fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalCertificate medicalCertificate = new MedicalCertificate();
        medicalCertificate.setId(id);
        return medicalCertificate;
    }
}
