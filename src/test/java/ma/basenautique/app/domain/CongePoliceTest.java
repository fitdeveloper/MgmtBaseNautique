package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class CongePoliceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CongePolice.class);
        CongePolice congePolice1 = new CongePolice();
        congePolice1.setId(1L);
        CongePolice congePolice2 = new CongePolice();
        congePolice2.setId(congePolice1.getId());
        assertThat(congePolice1).isEqualTo(congePolice2);
        congePolice2.setId(2L);
        assertThat(congePolice1).isNotEqualTo(congePolice2);
        congePolice1.setId(null);
        assertThat(congePolice1).isNotEqualTo(congePolice2);
    }
}
