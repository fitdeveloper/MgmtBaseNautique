package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.CongePolice} entity.
 */
public class CongePoliceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberCongePolice;

    @NotNull
    private LocalDate endDateCongePolice;

    @NotNull
    private Boolean validCongePolice;

    private String descCongePolice;


    private Long docId;

    private Long vehicleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberCongePolice() {
        return numberCongePolice;
    }

    public void setNumberCongePolice(String numberCongePolice) {
        this.numberCongePolice = numberCongePolice;
    }

    public LocalDate getEndDateCongePolice() {
        return endDateCongePolice;
    }

    public void setEndDateCongePolice(LocalDate endDateCongePolice) {
        this.endDateCongePolice = endDateCongePolice;
    }

    public Boolean isValidCongePolice() {
        return validCongePolice;
    }

    public void setValidCongePolice(Boolean validCongePolice) {
        this.validCongePolice = validCongePolice;
    }

    public String getDescCongePolice() {
        return descCongePolice;
    }

    public void setDescCongePolice(String descCongePolice) {
        this.descCongePolice = descCongePolice;
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
        if (!(o instanceof CongePoliceDTO)) {
            return false;
        }

        return id != null && id.equals(((CongePoliceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CongePoliceDTO{" +
            "id=" + getId() +
            ", numberCongePolice='" + getNumberCongePolice() + "'" +
            ", endDateCongePolice='" + getEndDateCongePolice() + "'" +
            ", validCongePolice='" + isValidCongePolice() + "'" +
            ", descCongePolice='" + getDescCongePolice() + "'" +
            ", docId=" + getDocId() +
            ", vehicleId=" + getVehicleId() +
            "}";
    }
}
