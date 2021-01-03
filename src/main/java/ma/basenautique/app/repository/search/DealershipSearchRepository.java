package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Dealership;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Dealership} entity.
 */
public interface DealershipSearchRepository extends ElasticsearchRepository<Dealership, Long> {
}
