package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class CongePoliceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CongePoliceDTO.class);
        CongePoliceDTO congePoliceDTO1 = new CongePoliceDTO();
        congePoliceDTO1.setId(1L);
        CongePoliceDTO congePoliceDTO2 = new CongePoliceDTO();
        assertThat(congePoliceDTO1).isNotEqualTo(congePoliceDTO2);
        congePoliceDTO2.setId(congePoliceDTO1.getId());
        assertThat(congePoliceDTO1).isEqualTo(congePoliceDTO2);
        congePoliceDTO2.setId(2L);
        assertThat(congePoliceDTO1).isNotEqualTo(congePoliceDTO2);
        congePoliceDTO1.setId(null);
        assertThat(congePoliceDTO1).isNotEqualTo(congePoliceDTO2);
    }
}
