package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.CongePolice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CongePolice} entity.
 */
public interface CongePoliceSearchRepository extends ElasticsearchRepository<CongePolice, Long> {
}
