package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.TypeDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TypeDoc} entity.
 */
public interface TypeDocSearchRepository extends ElasticsearchRepository<TypeDoc, Long> {
}
