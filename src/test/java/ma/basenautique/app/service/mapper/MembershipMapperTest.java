package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MembershipMapperTest {

    private MembershipMapper membershipMapper;

    @BeforeEach
    public void setUp() {
        membershipMapper = new MembershipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(membershipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(membershipMapper.fromId(null)).isNull();
    }
}
