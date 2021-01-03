package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.TypeDoc} entity.
 */
public class TypeDocDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberTypeDoc;

    @NotNull
    private String titleTypeDoc;

    private String descTypeDoc;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberTypeDoc() {
        return numberTypeDoc;
    }

    public void setNumberTypeDoc(String numberTypeDoc) {
        this.numberTypeDoc = numberTypeDoc;
    }

    public String getTitleTypeDoc() {
        return titleTypeDoc;
    }

    public void setTitleTypeDoc(String titleTypeDoc) {
        this.titleTypeDoc = titleTypeDoc;
    }

    public String getDescTypeDoc() {
        return descTypeDoc;
    }

    public void setDescTypeDoc(String descTypeDoc) {
        this.descTypeDoc = descTypeDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDocDTO)) {
            return false;
        }

        return id != null && id.equals(((TypeDocDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDocDTO{" +
            "id=" + getId() +
            ", numberTypeDoc='" + getNumberTypeDoc() + "'" +
            ", titleTypeDoc='" + getTitleTypeDoc() + "'" +
            ", descTypeDoc='" + getDescTypeDoc() + "'" +
            "}";
    }
}
