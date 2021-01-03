package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.MedicalCertificate} entity.
 */
public class MedicalCertificateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberMedicalCertificate;

    @NotNull
    private LocalDate endDateMedicalCertificate;

    @NotNull
    private Boolean validMedicalCertificate;

    private String descMedicalCertificate;


    private Long docId;

    private Long membershipId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMedicalCertificate() {
        return numberMedicalCertificate;
    }

    public void setNumberMedicalCertificate(String numberMedicalCertificate) {
        this.numberMedicalCertificate = numberMedicalCertificate;
    }

    public LocalDate getEndDateMedicalCertificate() {
        return endDateMedicalCertificate;
    }

    public void setEndDateMedicalCertificate(LocalDate endDateMedicalCertificate) {
        this.endDateMedicalCertificate = endDateMedicalCertificate;
    }

    public Boolean isValidMedicalCertificate() {
        return validMedicalCertificate;
    }

    public void setValidMedicalCertificate(Boolean validMedicalCertificate) {
        this.validMedicalCertificate = validMedicalCertificate;
    }

    public String getDescMedicalCertificate() {
        return descMedicalCertificate;
    }

    public void setDescMedicalCertificate(String descMedicalCertificate) {
        this.descMedicalCertificate = descMedicalCertificate;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalCertificateDTO)) {
            return false;
        }

        return id != null && id.equals(((MedicalCertificateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalCertificateDTO{" +
            "id=" + getId() +
            ", numberMedicalCertificate='" + getNumberMedicalCertificate() + "'" +
            ", endDateMedicalCertificate='" + getEndDateMedicalCertificate() + "'" +
            ", validMedicalCertificate='" + isValidMedicalCertificate() + "'" +
            ", descMedicalCertificate='" + getDescMedicalCertificate() + "'" +
            ", docId=" + getDocId() +
            ", membershipId=" + getMembershipId() +
            "}";
    }
}
