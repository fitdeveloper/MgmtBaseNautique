package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Association;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Association} entity.
 */
public interface AssociationSearchRepository extends ElasticsearchRepository<Association, Long> {
}
