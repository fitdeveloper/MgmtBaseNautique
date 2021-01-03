package ma.basenautique.app.repository;

import ma.basenautique.app.domain.TypeDoc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeDocRepository extends JpaRepository<TypeDoc, Long> {
}
