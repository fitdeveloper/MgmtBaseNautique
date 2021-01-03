package ma.basenautique.app.repository;

import ma.basenautique.app.domain.MedicalCertificate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
}
