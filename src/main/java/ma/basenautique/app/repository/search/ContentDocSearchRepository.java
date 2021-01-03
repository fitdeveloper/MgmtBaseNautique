package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.ContentDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ContentDoc} entity.
 */
public interface ContentDocSearchRepository extends ElasticsearchRepository<ContentDoc, Long> {
}
