package ma.basenautique.app.repository;

import ma.basenautique.app.domain.CongePolice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CongePolice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CongePoliceRepository extends JpaRepository<CongePolice, Long> {
}
