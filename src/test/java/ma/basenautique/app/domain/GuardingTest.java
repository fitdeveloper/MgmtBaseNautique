package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class GuardingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guarding.class);
        Guarding guarding1 = new Guarding();
        guarding1.setId(1L);
        Guarding guarding2 = new Guarding();
        guarding2.setId(guarding1.getId());
        assertThat(guarding1).isEqualTo(guarding2);
        guarding2.setId(2L);
        assertThat(guarding1).isNotEqualTo(guarding2);
        guarding1.setId(null);
        assertThat(guarding1).isNotEqualTo(guarding2);
    }
}
