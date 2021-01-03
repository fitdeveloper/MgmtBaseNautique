package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Association} entity.
 */
public class AssociationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberAssociation;

    @NotNull
    private String nameAssociation;

    @Lob
    private byte[] imageAssociation;

    private String imageAssociationContentType;
    private String descAssociation;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberAssociation() {
        return numberAssociation;
    }

    public void setNumberAssociation(String numberAssociation) {
        this.numberAssociation = numberAssociation;
    }

    public String getNameAssociation() {
        return nameAssociation;
    }

    public void setNameAssociation(String nameAssociation) {
        this.nameAssociation = nameAssociation;
    }

    public byte[] getImageAssociation() {
        return imageAssociation;
    }

    public void setImageAssociation(byte[] imageAssociation) {
        this.imageAssociation = imageAssociation;
    }

    public String getImageAssociationContentType() {
        return imageAssociationContentType;
    }

    public void setImageAssociationContentType(String imageAssociationContentType) {
        this.imageAssociationContentType = imageAssociationContentType;
    }

    public String getDescAssociation() {
        return descAssociation;
    }

    public void setDescAssociation(String descAssociation) {
        this.descAssociation = descAssociation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociationDTO)) {
            return false;
        }

        return id != null && id.equals(((AssociationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssociationDTO{" +
            "id=" + getId() +
            ", numberAssociation='" + getNumberAssociation() + "'" +
            ", nameAssociation='" + getNameAssociation() + "'" +
            ", imageAssociation='" + getImageAssociation() + "'" +
            ", descAssociation='" + getDescAssociation() + "'" +
            "}";
    }
}
