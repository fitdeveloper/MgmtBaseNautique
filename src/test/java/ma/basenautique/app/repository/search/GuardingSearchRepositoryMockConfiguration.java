package ma.basenautique.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GuardingSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GuardingSearchRepositoryMockConfiguration {

    @MockBean
    private GuardingSearchRepository mockGuardingSearchRepository;

}
