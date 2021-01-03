package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Doc.
 */
@Entity
@Table(name = "doc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "doc")
public class Doc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_doc", nullable = false, unique = true)
    private String numberDoc;

    @NotNull
    @Column(name = "title_doc", nullable = false)
    private String titleDoc;

    @NotNull
    @Column(name = "size_doc", nullable = false)
    private Long sizeDoc;

    @Column(name = "mime_type_doc")
    private String mimeTypeDoc;

    @Column(name = "desc_doc")
    private String descDoc;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ContentDoc contentDoc;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private TypeDoc typeDoc;

    @OneToOne(mappedBy = "doc")
    @JsonIgnore
    private Insurance insurance;

    @OneToOne(mappedBy = "doc")
    @JsonIgnore
    private CongePolice congePolice;

    @OneToOne(mappedBy = "doc")
    @JsonIgnore
    private MedicalCertificate medicalCertificate;

    @OneToOne(mappedBy = "doc")
    @JsonIgnore
    private Dealership dealership;

    @ManyToOne
    @JsonIgnoreProperties(value = "docs", allowSetters = true)
    private Membership membership;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberDoc() {
        return numberDoc;
    }

    public Doc numberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
        return this;
    }

    public void setNumberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
    }

    public String getTitleDoc() {
        return titleDoc;
    }

    public Doc titleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
        return this;
    }

    public void setTitleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
    }

    public Long getSizeDoc() {
        return sizeDoc;
    }

    public Doc sizeDoc(Long sizeDoc) {
        this.sizeDoc = sizeDoc;
        return this;
    }

    public void setSizeDoc(Long sizeDoc) {
        this.sizeDoc = sizeDoc;
    }

    public String getMimeTypeDoc() {
        return mimeTypeDoc;
    }

    public Doc mimeTypeDoc(String mimeTypeDoc) {
        this.mimeTypeDoc = mimeTypeDoc;
        return this;
    }

    public void setMimeTypeDoc(String mimeTypeDoc) {
        this.mimeTypeDoc = mimeTypeDoc;
    }

    public String getDescDoc() {
        return descDoc;
    }

    public Doc descDoc(String descDoc) {
        this.descDoc = descDoc;
        return this;
    }

    public void setDescDoc(String descDoc) {
        this.descDoc = descDoc;
    }

    public ContentDoc getContentDoc() {
        return contentDoc;
    }

    public Doc contentDoc(ContentDoc contentDoc) {
        this.contentDoc = contentDoc;
        return this;
    }

    public void setContentDoc(ContentDoc contentDoc) {
        this.contentDoc = contentDoc;
    }

    public TypeDoc getTypeDoc() {
        return typeDoc;
    }

    public Doc typeDoc(TypeDoc typeDoc) {
        this.typeDoc = typeDoc;
        return this;
    }

    public void setTypeDoc(TypeDoc typeDoc) {
        this.typeDoc = typeDoc;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public Doc insurance(Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public CongePolice getCongePolice() {
        return congePolice;
    }

    public Doc congePolice(CongePolice congePolice) {
        this.congePolice = congePolice;
        return this;
    }

    public void setCongePolice(CongePolice congePolice) {
        this.congePolice = congePolice;
    }

    public MedicalCertificate getMedicalCertificate() {
        return medicalCertificate;
    }

    public Doc medicalCertificate(MedicalCertificate medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
        return this;
    }

    public void setMedicalCertificate(MedicalCertificate medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }

    public Dealership getDealership() {
        return dealership;
    }

    public Doc dealership(Dealership dealership) {
        this.dealership = dealership;
        return this;
    }

    public void setDealership(Dealership dealership) {
        this.dealership = dealership;
    }

    public Membership getMembership() {
        return membership;
    }

    public Doc membership(Membership membership) {
        this.membership = membership;
        return this;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doc)) {
            return false;
        }
        return id != null && id.equals(((Doc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doc{" +
            "id=" + getId() +
            ", numberDoc='" + getNumberDoc() + "'" +
            ", titleDoc='" + getTitleDoc() + "'" +
            ", sizeDoc=" + getSizeDoc() +
            ", mimeTypeDoc='" + getMimeTypeDoc() + "'" +
            ", descDoc='" + getDescDoc() + "'" +
            "}";
    }
}
