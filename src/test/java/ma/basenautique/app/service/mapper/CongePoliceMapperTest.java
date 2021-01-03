package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CongePoliceMapperTest {

    private CongePoliceMapper congePoliceMapper;

    @BeforeEach
    public void setUp() {
        congePoliceMapper = new CongePoliceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(congePoliceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(congePoliceMapper.fromId(null)).isNull();
    }
}
