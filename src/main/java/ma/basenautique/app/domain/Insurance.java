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
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "insurance")
public class Insurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_insurance", nullable = false, unique = true)
    private String numberInsurance;

    @NotNull
    @Column(name = "end_date_insurance", nullable = false)
    private LocalDate endDateInsurance;

    @NotNull
    @Column(name = "valid_insurance", nullable = false)
    private Boolean validInsurance;

    @Column(name = "desc_insurance")
    private String descInsurance;

    @OneToOne
    @JoinColumn(unique = true)
    private Doc doc;

    @ManyToOne
    @JsonIgnoreProperties(value = "insurances", allowSetters = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberInsurance() {
        return numberInsurance;
    }

    public Insurance numberInsurance(String numberInsurance) {
        this.numberInsurance = numberInsurance;
        return this;
    }

    public void setNumberInsurance(String numberInsurance) {
        this.numberInsurance = numberInsurance;
    }

    public LocalDate getEndDateInsurance() {
        return endDateInsurance;
    }

    public Insurance endDateInsurance(LocalDate endDateInsurance) {
        this.endDateInsurance = endDateInsurance;
        return this;
    }

    public void setEndDateInsurance(LocalDate endDateInsurance) {
        this.endDateInsurance = endDateInsurance;
    }

    public Boolean isValidInsurance() {
        return validInsurance;
    }

    public Insurance validInsurance(Boolean validInsurance) {
        this.validInsurance = validInsurance;
        return this;
    }

    public void setValidInsurance(Boolean validInsurance) {
        this.validInsurance = validInsurance;
    }

    public String getDescInsurance() {
        return descInsurance;
    }

    public Insurance descInsurance(String descInsurance) {
        this.descInsurance = descInsurance;
        return this;
    }

    public void setDescInsurance(String descInsurance) {
        this.descInsurance = descInsurance;
    }

    public Doc getDoc() {
        return doc;
    }

    public Insurance doc(Doc doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Insurance vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Insurance)) {
            return false;
        }
        return id != null && id.equals(((Insurance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + getId() +
            ", numberInsurance='" + getNumberInsurance() + "'" +
            ", endDateInsurance='" + getEndDateInsurance() + "'" +
            ", validInsurance='" + isValidInsurance() + "'" +
            ", descInsurance='" + getDescInsurance() + "'" +
            "}";
    }
}
