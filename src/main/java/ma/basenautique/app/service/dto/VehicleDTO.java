package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Vehicle} entity.
 */
public class VehicleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberVehicle;

    private String titleVehicle;

    private String descVehicle;


    private Long membershipId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberVehicle() {
        return numberVehicle;
    }

    public void setNumberVehicle(String numberVehicle) {
        this.numberVehicle = numberVehicle;
    }

    public String getTitleVehicle() {
        return titleVehicle;
    }

    public void setTitleVehicle(String titleVehicle) {
        this.titleVehicle = titleVehicle;
    }

    public String getDescVehicle() {
        return descVehicle;
    }

    public void setDescVehicle(String descVehicle) {
        this.descVehicle = descVehicle;
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
        if (!(o instanceof VehicleDTO)) {
            return false;
        }

        return id != null && id.equals(((VehicleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", numberVehicle='" + getNumberVehicle() + "'" +
            ", titleVehicle='" + getTitleVehicle() + "'" +
            ", descVehicle='" + getDescVehicle() + "'" +
            ", membershipId=" + getMembershipId() +
            "}";
    }
}
