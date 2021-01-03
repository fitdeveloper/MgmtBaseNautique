package ma.basenautique.app.repository;

import ma.basenautique.app.domain.Guarding;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Guarding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuardingRepository extends JpaRepository<Guarding, Long> {
}
