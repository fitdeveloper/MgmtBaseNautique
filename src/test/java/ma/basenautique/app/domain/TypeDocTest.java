package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class TypeDocTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDoc.class);
        TypeDoc typeDoc1 = new TypeDoc();
        typeDoc1.setId(1L);
        TypeDoc typeDoc2 = new TypeDoc();
        typeDoc2.setId(typeDoc1.getId());
        assertThat(typeDoc1).isEqualTo(typeDoc2);
        typeDoc2.setId(2L);
        assertThat(typeDoc1).isNotEqualTo(typeDoc2);
        typeDoc1.setId(null);
        assertThat(typeDoc1).isNotEqualTo(typeDoc2);
    }
}
