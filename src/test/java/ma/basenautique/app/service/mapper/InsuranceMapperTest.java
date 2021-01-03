package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InsuranceMapperTest {

    private InsuranceMapper insuranceMapper;

    @BeforeEach
    public void setUp() {
        insuranceMapper = new InsuranceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(insuranceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(insuranceMapper.fromId(null)).isNull();
    }
}
