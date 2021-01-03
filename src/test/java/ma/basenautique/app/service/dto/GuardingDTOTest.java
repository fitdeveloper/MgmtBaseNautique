package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class GuardingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuardingDTO.class);
        GuardingDTO guardingDTO1 = new GuardingDTO();
        guardingDTO1.setId(1L);
        GuardingDTO guardingDTO2 = new GuardingDTO();
        assertThat(guardingDTO1).isNotEqualTo(guardingDTO2);
        guardingDTO2.setId(guardingDTO1.getId());
        assertThat(guardingDTO1).isEqualTo(guardingDTO2);
        guardingDTO2.setId(2L);
        assertThat(guardingDTO1).isNotEqualTo(guardingDTO2);
        guardingDTO1.setId(null);
        assertThat(guardingDTO1).isNotEqualTo(guardingDTO2);
    }
}
