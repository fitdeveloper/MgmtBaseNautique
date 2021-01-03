package ma.basenautique.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.basenautique.app.web.rest.TestUtil;

public class MedicalCertificateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalCertificateDTO.class);
        MedicalCertificateDTO medicalCertificateDTO1 = new MedicalCertificateDTO();
        medicalCertificateDTO1.setId(1L);
        MedicalCertificateDTO medicalCertificateDTO2 = new MedicalCertificateDTO();
        assertThat(medicalCertificateDTO1).isNotEqualTo(medicalCertificateDTO2);
        medicalCertificateDTO2.setId(medicalCertificateDTO1.getId());
        assertThat(medicalCertificateDTO1).isEqualTo(medicalCertificateDTO2);
        medicalCertificateDTO2.setId(2L);
        assertThat(medicalCertificateDTO1).isNotEqualTo(medicalCertificateDTO2);
        medicalCertificateDTO1.setId(null);
        assertThat(medicalCertificateDTO1).isNotEqualTo(medicalCertificateDTO2);
    }
}
