package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Doc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Doc} entity.
 */
public interface DocSearchRepository extends ElasticsearchRepository<Doc, Long> {
}
