package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ContentDoc.
 */
@Entity
@Table(name = "content_doc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "contentdoc")
public class ContentDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_content_doc", nullable = false, unique = true)
    private String numberContentDoc;

    
    @Lob
    @Column(name = "data_content_doc", nullable = false)
    private byte[] dataContentDoc;

    @Column(name = "data_content_doc_content_type", nullable = false)
    private String dataContentDocContentType;

    @OneToOne(mappedBy = "contentDoc")
    @JsonIgnore
    private Doc doc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberContentDoc() {
        return numberContentDoc;
    }

    public ContentDoc numberContentDoc(String numberContentDoc) {
        this.numberContentDoc = numberContentDoc;
        return this;
    }

    public void setNumberContentDoc(String numberContentDoc) {
        this.numberContentDoc = numberContentDoc;
    }

    public byte[] getDataContentDoc() {
        return dataContentDoc;
    }

    public ContentDoc dataContentDoc(byte[] dataContentDoc) {
        this.dataContentDoc = dataContentDoc;
        return this;
    }

    public void setDataContentDoc(byte[] dataContentDoc) {
        this.dataContentDoc = dataContentDoc;
    }

    public String getDataContentDocContentType() {
        return dataContentDocContentType;
    }

    public ContentDoc dataContentDocContentType(String dataContentDocContentType) {
        this.dataContentDocContentType = dataContentDocContentType;
        return this;
    }

    public void setDataContentDocContentType(String dataContentDocContentType) {
        this.dataContentDocContentType = dataContentDocContentType;
    }

    public Doc getDoc() {
        return doc;
    }

    public ContentDoc doc(Doc doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDoc)) {
            return false;
        }
        return id != null && id.equals(((ContentDoc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDoc{" +
            "id=" + getId() +
            ", numberContentDoc='" + getNumberContentDoc() + "'" +
            ", dataContentDoc='" + getDataContentDoc() + "'" +
            ", dataContentDocContentType='" + getDataContentDocContentType() + "'" +
            "}";
    }
}
