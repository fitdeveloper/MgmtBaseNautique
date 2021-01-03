package ma.basenautique.app.repository.search;

import ma.basenautique.app.domain.MedicalCertificate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MedicalCertificate} entity.
 */
public interface MedicalCertificateSearchRepository extends ElasticsearchRepository<MedicalCertificate, Long> {
}
