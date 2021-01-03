package ma.basenautique.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TypeDocSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TypeDocSearchRepositoryMockConfiguration {

    @MockBean
    private TypeDocSearchRepository mockTypeDocSearchRepository;

}
