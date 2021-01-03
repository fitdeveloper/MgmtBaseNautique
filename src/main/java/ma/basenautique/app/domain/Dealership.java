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
 * A Dealership.
 */
@Entity
@Table(name = "dealership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dealership")
public class Dealership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_dealership", nullable = false, unique = true)
    private String numberDealership;

    @NotNull
    @Column(name = "end_date_dealership", nullable = false)
    private LocalDate endDateDealership;

    @NotNull
    @Column(name = "valid_dealership", nullable = false)
    private Boolean validDealership;

    @Column(name = "desc_dealership")
    private String descDealership;

    @OneToOne
    @JoinColumn(unique = true)
    private Doc doc;

    @ManyToOne
    @JsonIgnoreProperties(value = "dealerships", allowSetters = true)
    private Membership membership;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberDealership() {
        return numberDealership;
    }

    public Dealership numberDealership(String numberDealership) {
        this.numberDealership = numberDealership;
        return this;
    }

    public void setNumberDealership(String numberDealership) {
        this.numberDealership = numberDealership;
    }

    public LocalDate getEndDateDealership() {
        return endDateDealership;
    }

    public Dealership endDateDealership(LocalDate endDateDealership) {
        this.endDateDealership = endDateDealership;
        return this;
    }

    public void setEndDateDealership(LocalDate endDateDealership) {
        this.endDateDealership = endDateDealership;
    }

    public Boolean isValidDealership() {
        return validDealership;
    }

    public Dealership validDealership(Boolean validDealership) {
        this.validDealership = validDealership;
        return this;
    }

    public void setValidDealership(Boolean validDealership) {
        this.validDealership = validDealership;
    }

    public String getDescDealership() {
        return descDealership;
    }

    public Dealership descDealership(String descDealership) {
        this.descDealership = descDealership;
        return this;
    }

    public void setDescDealership(String descDealership) {
        this.descDealership = descDealership;
    }

    public Doc getDoc() {
        return doc;
    }

    public Dealership doc(Doc doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Membership getMembership() {
        return membership;
    }

    public Dealership membership(Membership membership) {
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
        if (!(o instanceof Dealership)) {
            return false;
        }
        return id != null && id.equals(((Dealership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dealership{" +
            "id=" + getId() +
            ", numberDealership='" + getNumberDealership() + "'" +
            ", endDateDealership='" + getEndDateDealership() + "'" +
            ", validDealership='" + isValidDealership() + "'" +
            ", descDealership='" + getDescDealership() + "'" +
            "}";
    }
}
