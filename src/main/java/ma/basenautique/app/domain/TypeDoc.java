package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TypeDoc.
 */
@Entity
@Table(name = "type_doc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "typedoc")
public class TypeDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_type_doc", nullable = false, unique = true)
    private String numberTypeDoc;

    @NotNull
    @Column(name = "title_type_doc", nullable = false)
    private String titleTypeDoc;

    @Column(name = "desc_type_doc")
    private String descTypeDoc;

    @OneToOne(mappedBy = "typeDoc")
    @JsonIgnore
    private Doc doc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberTypeDoc() {
        return numberTypeDoc;
    }

    public TypeDoc numberTypeDoc(String numberTypeDoc) {
        this.numberTypeDoc = numberTypeDoc;
        return this;
    }

    public void setNumberTypeDoc(String numberTypeDoc) {
        this.numberTypeDoc = numberTypeDoc;
    }

    public String getTitleTypeDoc() {
        return titleTypeDoc;
    }

    public TypeDoc titleTypeDoc(String titleTypeDoc) {
        this.titleTypeDoc = titleTypeDoc;
        return this;
    }

    public void setTitleTypeDoc(String titleTypeDoc) {
        this.titleTypeDoc = titleTypeDoc;
    }

    public String getDescTypeDoc() {
        return descTypeDoc;
    }

    public TypeDoc descTypeDoc(String descTypeDoc) {
        this.descTypeDoc = descTypeDoc;
        return this;
    }

    public void setDescTypeDoc(String descTypeDoc) {
        this.descTypeDoc = descTypeDoc;
    }

    public Doc getDoc() {
        return doc;
    }

    public TypeDoc doc(Doc doc) {
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
        if (!(o instanceof TypeDoc)) {
            return false;
        }
        return id != null && id.equals(((TypeDoc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDoc{" +
            "id=" + getId() +
            ", numberTypeDoc='" + getNumberTypeDoc() + "'" +
            ", titleTypeDoc='" + getTitleTypeDoc() + "'" +
            ", descTypeDoc='" + getDescTypeDoc() + "'" +
            "}";
    }
}
