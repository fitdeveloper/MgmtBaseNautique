package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class AssociationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociationDTO.class);
        AssociationDTO associationDTO1 = new AssociationDTO();
        associationDTO1.setId(1L);
        AssociationDTO associationDTO2 = new AssociationDTO();
        assertThat(associationDTO1).isNotEqualTo(associationDTO2);
        associationDTO2.setId(associationDTO1.getId());
        assertThat(associationDTO1).isEqualTo(associationDTO2);
        associationDTO2.setId(2L);
        assertThat(associationDTO1).isNotEqualTo(associationDTO2);
        associationDTO1.setId(null);
        assertThat(associationDTO1).isNotEqualTo(associationDTO2);
    }
}
