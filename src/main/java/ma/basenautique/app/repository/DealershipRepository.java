package ma.basenautique.app.repository;

import ma.basenautique.app.domain.Dealership;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dealership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Long> {
}
