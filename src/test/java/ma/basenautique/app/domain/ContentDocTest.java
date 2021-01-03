package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class ContentDocTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDoc.class);
        ContentDoc contentDoc1 = new ContentDoc();
        contentDoc1.setId(1L);
        ContentDoc contentDoc2 = new ContentDoc();
        contentDoc2.setId(contentDoc1.getId());
        assertThat(contentDoc1).isEqualTo(contentDoc2);
        contentDoc2.setId(2L);
        assertThat(contentDoc1).isNotEqualTo(contentDoc2);
        contentDoc1.setId(null);
        assertThat(contentDoc1).isNotEqualTo(contentDoc2);
    }
}
