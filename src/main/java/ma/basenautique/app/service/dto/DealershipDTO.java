package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Dealership} entity.
 */
public class DealershipDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberDealership;

    @NotNull
    private LocalDate endDateDealership;

    @NotNull
    private Boolean validDealership;

    private String descDealership;


    private Long docId;

    private Long membershipId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberDealership() {
        return numberDealership;
    }

    public void setNumberDealership(String numberDealership) {
        this.numberDealership = numberDealership;
    }

    public LocalDate getEndDateDealership() {
        return endDateDealership;
    }

    public void setEndDateDealership(LocalDate endDateDealership) {
        this.endDateDealership = endDateDealership;
    }

    public Boolean isValidDealership() {
        return validDealership;
    }

    public void setValidDealership(Boolean validDealership) {
        this.validDealership = validDealership;
    }

    public String getDescDealership() {
        return descDealership;
    }

    public void setDescDealership(String descDealership) {
        this.descDealership = descDealership;
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
        if (!(o instanceof DealershipDTO)) {
            return false;
        }

        return id != null && id.equals(((DealershipDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealershipDTO{" +
            "id=" + getId() +
            ", numberDealership='" + getNumberDealership() + "'" +
            ", endDateDealership='" + getEndDateDealership() + "'" +
            ", validDealership='" + isValidDealership() + "'" +
            ", descDealership='" + getDescDealership() + "'" +
            ", docId=" + getDocId() +
            ", membershipId=" + getMembershipId() +
            "}";
    }
}
