package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Guarding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Guarding} entity.
 */
public interface GuardingSearchRepository extends ElasticsearchRepository<Guarding, Long> {
}
