package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.Membership;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Membership} entity.
 */
public interface MembershipSearchRepository extends ElasticsearchRepository<Membership, Long> {
}
