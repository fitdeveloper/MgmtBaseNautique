package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Member;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Member} entity.
 */
public interface MemberSearchRepository extends ElasticsearchRepository<Member, Long> {
}
