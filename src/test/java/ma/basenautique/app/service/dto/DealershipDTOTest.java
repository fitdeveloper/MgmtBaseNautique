package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class DealershipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealershipDTO.class);
        DealershipDTO dealershipDTO1 = new DealershipDTO();
        dealershipDTO1.setId(1L);
        DealershipDTO dealershipDTO2 = new DealershipDTO();
        assertThat(dealershipDTO1).isNotEqualTo(dealershipDTO2);
        dealershipDTO2.setId(dealershipDTO1.getId());
        assertThat(dealershipDTO1).isEqualTo(dealershipDTO2);
        dealershipDTO2.setId(2L);
        assertThat(dealershipDTO1).isNotEqualTo(dealershipDTO2);
        dealershipDTO1.setId(null);
        assertThat(dealershipDTO1).isNotEqualTo(dealershipDTO2);
    }
}
