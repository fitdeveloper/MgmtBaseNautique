package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeDocMapperTest {

    private TypeDocMapper typeDocMapper;

    @BeforeEach
    public void setUp() {
        typeDocMapper = new TypeDocMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeDocMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeDocMapper.fromId(null)).isNull();
    }
}
