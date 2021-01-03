package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Membership} entity.
 */
public class MembershipDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberMembership;

    private BigDecimal amountMembership;

    @NotNull
    private LocalDate startDateMembership;

    @NotNull
    private LocalDate endDateMembership;

    @NotNull
    private Boolean validMembership;

    private String descMembership;


    private Long memberId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMembership() {
        return numberMembership;
    }

    public void setNumberMembership(String numberMembership) {
        this.numberMembership = numberMembership;
    }

    public BigDecimal getAmountMembership() {
        return amountMembership;
    }

    public void setAmountMembership(BigDecimal amountMembership) {
        this.amountMembership = amountMembership;
    }

    public LocalDate getStartDateMembership() {
        return startDateMembership;
    }

    public void setStartDateMembership(LocalDate startDateMembership) {
        this.startDateMembership = startDateMembership;
    }

    public LocalDate getEndDateMembership() {
        return endDateMembership;
    }

    public void setEndDateMembership(LocalDate endDateMembership) {
        this.endDateMembership = endDateMembership;
    }

    public Boolean isValidMembership() {
        return validMembership;
    }

    public void setValidMembership(Boolean validMembership) {
        this.validMembership = validMembership;
    }

    public String getDescMembership() {
        return descMembership;
    }

    public void setDescMembership(String descMembership) {
        this.descMembership = descMembership;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipDTO)) {
            return false;
        }

        return id != null && id.equals(((MembershipDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipDTO{" +
            "id=" + getId() +
            ", numberMembership='" + getNumberMembership() + "'" +
            ", amountMembership=" + getAmountMembership() +
            ", startDateMembership='" + getStartDateMembership() + "'" +
            ", endDateMembership='" + getEndDateMembership() + "'" +
            ", validMembership='" + isValidMembership() + "'" +
            ", descMembership='" + getDescMembership() + "'" +
            ", memberId=" + getMemberId() +
            "}";
    }
}
