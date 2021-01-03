package ma.basenautique.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicalCertificateMapperTest {

    private MedicalCertificateMapper medicalCertificateMapper;

    @BeforeEach
    public void setUp() {
        medicalCertificateMapper = new MedicalCertificateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicalCertificateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicalCertificateMapper.fromId(null)).isNull();
    }
}
