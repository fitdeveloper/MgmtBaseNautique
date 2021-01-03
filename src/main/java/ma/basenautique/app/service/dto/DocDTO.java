package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Doc} entity.
 */
public class DocDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberDoc;

    @NotNull
    private String titleDoc;

    @NotNull
    private Long sizeDoc;

    private String mimeTypeDoc;

    private String descDoc;


    private Long contentDocId;

    private Long typeDocId;

    private Long membershipId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberDoc() {
        return numberDoc;
    }

    public void setNumberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
    }

    public String getTitleDoc() {
        return titleDoc;
    }

    public void setTitleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
    }

    public Long getSizeDoc() {
        return sizeDoc;
    }

    public void setSizeDoc(Long sizeDoc) {
        this.sizeDoc = sizeDoc;
    }

    public String getMimeTypeDoc() {
        return mimeTypeDoc;
    }

    public void setMimeTypeDoc(String mimeTypeDoc) {
        this.mimeTypeDoc = mimeTypeDoc;
    }

    public String getDescDoc() {
        return descDoc;
    }

    public void setDescDoc(String descDoc) {
        this.descDoc = descDoc;
    }

    public Long getContentDocId() {
        return contentDocId;
    }

    public void setContentDocId(Long contentDocId) {
        this.contentDocId = contentDocId;
    }

    public Long getTypeDocId() {
        return typeDocId;
    }

    public void setTypeDocId(Long typeDocId) {
        this.typeDocId = typeDocId;
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
        if (!(o instanceof DocDTO)) {
            return false;
        }

        return id != null && id.equals(((DocDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocDTO{" +
            "id=" + getId() +
            ", numberDoc='" + getNumberDoc() + "'" +
            ", titleDoc='" + getTitleDoc() + "'" +
            ", sizeDoc=" + getSizeDoc() +
            ", mimeTypeDoc='" + getMimeTypeDoc() + "'" +
            ", descDoc='" + getDescDoc() + "'" +
            ", contentDocId=" + getContentDocId() +
            ", typeDocId=" + getTypeDocId() +
            ", membershipId=" + getMembershipId() +
            "}";
    }
}
