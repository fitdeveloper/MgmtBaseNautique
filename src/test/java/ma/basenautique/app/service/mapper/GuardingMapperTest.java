package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GuardingMapperTest {

    private GuardingMapper guardingMapper;

    @BeforeEach
    public void setUp() {
        guardingMapper = new GuardingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(guardingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(guardingMapper.fromId(null)).isNull();
    }
}
