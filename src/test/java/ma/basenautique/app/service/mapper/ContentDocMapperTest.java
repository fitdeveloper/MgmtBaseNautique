package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentDocMapperTest {

    private ContentDocMapper contentDocMapper;

    @BeforeEach
    public void setUp() {
        contentDocMapper = new ContentDocMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contentDocMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contentDocMapper.fromId(null)).isNull();
    }
}
