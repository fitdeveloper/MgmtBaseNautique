package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AssociationMapperTest {

    private AssociationMapper associationMapper;

    @BeforeEach
    public void setUp() {
        associationMapper = new AssociationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(associationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(associationMapper.fromId(null)).isNull();
    }
}
