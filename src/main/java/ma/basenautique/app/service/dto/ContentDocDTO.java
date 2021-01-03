package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link ma.basenautique.app.domain.ContentDoc} entity.
 */
public class ContentDocDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberContentDoc;

    
    @Lob
    private byte[] dataContentDoc;

    private String dataContentDocContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberContentDoc() {
        return numberContentDoc;
    }

    public void setNumberContentDoc(String numberContentDoc) {
        this.numberContentDoc = numberContentDoc;
    }

    public byte[] getDataContentDoc() {
        return dataContentDoc;
    }

    public void setDataContentDoc(byte[] dataContentDoc) {
        this.dataContentDoc = dataContentDoc;
    }

    public String getDataContentDocContentType() {
        return dataContentDocContentType;
    }

    public void setDataContentDocContentType(String dataContentDocContentType) {
        this.dataContentDocContentType = dataContentDocContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDocDTO)) {
            return false;
        }

        return id != null && id.equals(((ContentDocDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDocDTO{" +
            "id=" + getId() +
            ", numberContentDoc='" + getNumberContentDoc() + "'" +
            ", dataContentDoc='" + getDataContentDoc() + "'" +
            "}";
    }
}
