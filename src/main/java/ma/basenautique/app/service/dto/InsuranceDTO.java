package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Insurance} entity.
 */
public class InsuranceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberInsurance;

    @NotNull
    private LocalDate endDateInsurance;

    @NotNull
    private Boolean validInsurance;

    private String descInsurance;


    private Long docId;

    private Long vehicleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberInsurance() {
        return numberInsurance;
    }

    public void setNumberInsurance(String numberInsurance) {
        this.numberInsurance = numberInsurance;
    }

    public LocalDate getEndDateInsurance() {
        return endDateInsurance;
    }

    public void setEndDateInsurance(LocalDate endDateInsurance) {
        this.endDateInsurance = endDateInsurance;
    }

    public Boolean isValidInsurance() {
        return validInsurance;
    }

    public void setValidInsurance(Boolean validInsurance) {
        this.validInsurance = validInsurance;
    }

    public String getDescInsurance() {
        return descInsurance;
    }

    public void setDescInsurance(String descInsurance) {
        this.descInsurance = descInsurance;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InsuranceDTO)) {
            return false;
        }

        return id != null && id.equals(((InsuranceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InsuranceDTO{" +
            "id=" + getId() +
            ", numberInsurance='" + getNumberInsurance() + "'" +
            ", endDateInsurance='" + getEndDateInsurance() + "'" +
            ", validInsurance='" + isValidInsurance() + "'" +
            ", descInsurance='" + getDescInsurance() + "'" +
            ", docId=" + getDocId() +
            ", vehicleId=" + getVehicleId() +
            "}";
    }
}
