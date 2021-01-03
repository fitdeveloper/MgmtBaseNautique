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
 * A CongePolice.
 */
@Entity
@Table(name = "conge_police")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "congepolice")
public class CongePolice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_conge_police", nullable = false, unique = true)
    private String numberCongePolice;

    @NotNull
    @Column(name = "end_date_conge_police", nullable = false)
    private LocalDate endDateCongePolice;

    @NotNull
    @Column(name = "valid_conge_police", nullable = false)
    private Boolean validCongePolice;

    @Column(name = "desc_conge_police")
    private String descCongePolice;

    @OneToOne
    @JoinColumn(unique = true)
    private Doc doc;

    @ManyToOne
    @JsonIgnoreProperties(value = "congePolices", allowSetters = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberCongePolice() {
        return numberCongePolice;
    }

    public CongePolice numberCongePolice(String numberCongePolice) {
        this.numberCongePolice = numberCongePolice;
        return this;
    }

    public void setNumberCongePolice(String numberCongePolice) {
        this.numberCongePolice = numberCongePolice;
    }

    public LocalDate getEndDateCongePolice() {
        return endDateCongePolice;
    }

    public CongePolice endDateCongePolice(LocalDate endDateCongePolice) {
        this.endDateCongePolice = endDateCongePolice;
        return this;
    }

    public void setEndDateCongePolice(LocalDate endDateCongePolice) {
        this.endDateCongePolice = endDateCongePolice;
    }

    public Boolean isValidCongePolice() {
        return validCongePolice;
    }

    public CongePolice validCongePolice(Boolean validCongePolice) {
        this.validCongePolice = validCongePolice;
        return this;
    }

    public void setValidCongePolice(Boolean validCongePolice) {
        this.validCongePolice = validCongePolice;
    }

    public String getDescCongePolice() {
        return descCongePolice;
    }

    public CongePolice descCongePolice(String descCongePolice) {
        this.descCongePolice = descCongePolice;
        return this;
    }

    public void setDescCongePolice(String descCongePolice) {
        this.descCongePolice = descCongePolice;
    }

    public Doc getDoc() {
        return doc;
    }

    public CongePolice doc(Doc doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public CongePolice vehicle(Vehicle vehicle) {
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
        if (!(o instanceof CongePolice)) {
            return false;
        }
        return id != null && id.equals(((CongePolice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CongePolice{" +
            "id=" + getId() +
            ", numberCongePolice='" + getNumberCongePolice() + "'" +
            ", endDateCongePolice='" + getEndDateCongePolice() + "'" +
            ", validCongePolice='" + isValidCongePolice() + "'" +
            ", descCongePolice='" + getDescCongePolice() + "'" +
            "}";
    }
}
