package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class DealershipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dealership.class);
        Dealership dealership1 = new Dealership();
        dealership1.setId(1L);
        Dealership dealership2 = new Dealership();
        dealership2.setId(dealership1.getId());
        assertThat(dealership1).isEqualTo(dealership2);
        dealership2.setId(2L);
        assertThat(dealership1).isNotEqualTo(dealership2);
        dealership1.setId(null);
        assertThat(dealership1).isNotEqualTo(dealership2);
    }
}
