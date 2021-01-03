package ma.basenautique.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class MedicalCertificateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalCertificate.class);
        MedicalCertificate medicalCertificate1 = new MedicalCertificate();
        medicalCertificate1.setId(1L);
        MedicalCertificate medicalCertificate2 = new MedicalCertificate();
        medicalCertificate2.setId(medicalCertificate1.getId());
        assertThat(medicalCertificate1).isEqualTo(medicalCertificate2);
        medicalCertificate2.setId(2L);
        assertThat(medicalCertificate1).isNotEqualTo(medicalCertificate2);
        medicalCertificate1.setId(null);
        assertThat(medicalCertificate1).isNotEqualTo(medicalCertificate2);
    }
}
