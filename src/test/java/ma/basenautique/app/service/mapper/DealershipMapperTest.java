package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DealershipMapperTest {

    private DealershipMapper dealershipMapper;

    @BeforeEach
    public void setUp() {
        dealershipMapper = new DealershipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dealershipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dealershipMapper.fromId(null)).isNull();
    }
}
