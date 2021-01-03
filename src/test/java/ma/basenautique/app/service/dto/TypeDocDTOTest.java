package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class TypeDocDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDocDTO.class);
        TypeDocDTO typeDocDTO1 = new TypeDocDTO();
        typeDocDTO1.setId(1L);
        TypeDocDTO typeDocDTO2 = new TypeDocDTO();
        assertThat(typeDocDTO1).isNotEqualTo(typeDocDTO2);
        typeDocDTO2.setId(typeDocDTO1.getId());
        assertThat(typeDocDTO1).isEqualTo(typeDocDTO2);
        typeDocDTO2.setId(2L);
        assertThat(typeDocDTO1).isNotEqualTo(typeDocDTO2);
        typeDocDTO1.setId(null);
        assertThat(typeDocDTO1).isNotEqualTo(typeDocDTO2);
    }
}
