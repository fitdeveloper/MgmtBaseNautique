package ma.basenautique.app.repository;

import ma.basenautique.app.domain.ContentDoc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContentDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentDocRepository extends JpaRepository<ContentDoc, Long> {
}
