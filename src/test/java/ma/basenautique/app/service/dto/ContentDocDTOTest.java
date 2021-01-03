package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class ContentDocDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDocDTO.class);
        ContentDocDTO contentDocDTO1 = new ContentDocDTO();
        contentDocDTO1.setId(1L);
        ContentDocDTO contentDocDTO2 = new ContentDocDTO();
        assertThat(contentDocDTO1).isNotEqualTo(contentDocDTO2);
        contentDocDTO2.setId(contentDocDTO1.getId());
        assertThat(contentDocDTO1).isEqualTo(contentDocDTO2);
        contentDocDTO2.setId(2L);
        assertThat(contentDocDTO1).isNotEqualTo(contentDocDTO2);
        contentDocDTO1.setId(null);
        assertThat(contentDocDTO1).isNotEqualTo(contentDocDTO2);
    }
}
