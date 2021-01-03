package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MedicalCertificate.
 */
@Entity
@Table(name = "medical_certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalcertificate")
public class MedicalCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_medical_certificate", nullable = false, unique = true)
    private String numberMedicalCertificate;

    @NotNull
    @Column(name = "end_date_medical_certificate", nullable = false)
    private LocalDate endDateMedicalCertificate;

    @NotNull
    @Column(name = "valid_medical_certificate", nullable = false)
    private Boolean validMedicalCertificate;

    @Column(name = "desc_medical_certificate")
    private String descMedicalCertificate;

    @OneToOne
    @JoinColumn(unique = true)
    private Doc doc;

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalCertificates", allowSetters = true)
    private Membership membership;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMedicalCertificate() {
        return numberMedicalCertificate;
    }

    public MedicalCertificate numberMedicalCertificate(String numberMedicalCertificate) {
        this.numberMedicalCertificate = numberMedicalCertificate;
        return this;
    }

    public void setNumberMedicalCertificate(String numberMedicalCertificate) {
        this.numberMedicalCertificate = numberMedicalCertificate;
    }

    public LocalDate getEndDateMedicalCertificate() {
        return endDateMedicalCertificate;
    }

    public MedicalCertificate endDateMedicalCertificate(LocalDate endDateMedicalCertificate) {
        this.endDateMedicalCertificate = endDateMedicalCertificate;
        return this;
    }

    public void setEndDateMedicalCertificate(LocalDate endDateMedicalCertificate) {
        this.endDateMedicalCertificate = endDateMedicalCertificate;
    }

    public Boolean isValidMedicalCertificate() {
        return validMedicalCertificate;
    }

    public MedicalCertificate validMedicalCertificate(Boolean validMedicalCertificate) {
        this.validMedicalCertificate = validMedicalCertificate;
        return this;
    }

    public void setValidMedicalCertificate(Boolean validMedicalCertificate) {
        this.validMedicalCertificate = validMedicalCertificate;
    }

    public String getDescMedicalCertificate() {
        return descMedicalCertificate;
    }

    public MedicalCertificate descMedicalCertificate(String descMedicalCertificate) {
        this.descMedicalCertificate = descMedicalCertificate;
        return this;
    }

    public void setDescMedicalCertificate(String descMedicalCertificate) {
        this.descMedicalCertificate = descMedicalCertificate;
    }

    public Doc getDoc() {
        return doc;
    }

    public MedicalCertificate doc(Doc doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Membership getMembership() {
        return membership;
    }

    public MedicalCertificate membership(Membership membership) {
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
        if (!(o instanceof MedicalCertificate)) {
            return false;
        }
        return id != null && id.equals(((MedicalCertificate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalCertificate{" +
            "id=" + getId() +
            ", numberMedicalCertificate='" + getNumberMedicalCertificate() + "'" +
            ", endDateMedicalCertificate='" + getEndDateMedicalCertificate() + "'" +
            ", validMedicalCertificate='" + isValidMedicalCertificate() + "'" +
            ", descMedicalCertificate='" + getDescMedicalCertificate() + "'" +
            "}";
    }
}
