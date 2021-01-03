package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_vehicle", nullable = false, unique = true)
    private String numberVehicle;

    @Column(name = "title_vehicle")
    private String titleVehicle;

    @Column(name = "desc_vehicle")
    private String descVehicle;

    @OneToMany(mappedBy = "vehicle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CongePolice> congePolices = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Insurance> insurances = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "vehicles", allowSetters = true)
    private Membership membership;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberVehicle() {
        return numberVehicle;
    }

    public Vehicle numberVehicle(String numberVehicle) {
        this.numberVehicle = numberVehicle;
        return this;
    }

    public void setNumberVehicle(String numberVehicle) {
        this.numberVehicle = numberVehicle;
    }

    public String getTitleVehicle() {
        return titleVehicle;
    }

    public Vehicle titleVehicle(String titleVehicle) {
        this.titleVehicle = titleVehicle;
        return this;
    }

    public void setTitleVehicle(String titleVehicle) {
        this.titleVehicle = titleVehicle;
    }

    public String getDescVehicle() {
        return descVehicle;
    }

    public Vehicle descVehicle(String descVehicle) {
        this.descVehicle = descVehicle;
        return this;
    }

    public void setDescVehicle(String descVehicle) {
        this.descVehicle = descVehicle;
    }

    public Set<CongePolice> getCongePolices() {
        return congePolices;
    }

    public Vehicle congePolices(Set<CongePolice> congePolices) {
        this.congePolices = congePolices;
        return this;
    }

    public Vehicle addCongePolice(CongePolice congePolice) {
        this.congePolices.add(congePolice);
        congePolice.setVehicle(this);
        return this;
    }

    public Vehicle removeCongePolice(CongePolice congePolice) {
        this.congePolices.remove(congePolice);
        congePolice.setVehicle(null);
        return this;
    }

    public void setCongePolices(Set<CongePolice> congePolices) {
        this.congePolices = congePolices;
    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public Vehicle insurances(Set<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

    public Vehicle addInsurance(Insurance insurance) {
        this.insurances.add(insurance);
        insurance.setVehicle(this);
        return this;
    }

    public Vehicle removeInsurance(Insurance insurance) {
        this.insurances.remove(insurance);
        insurance.setVehicle(null);
        return this;
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Membership getMembership() {
        return membership;
    }

    public Vehicle membership(Membership membership) {
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
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", numberVehicle='" + getNumberVehicle() + "'" +
            ", titleVehicle='" + getTitleVehicle() + "'" +
            ", descVehicle='" + getDescVehicle() + "'" +
            "}";
    }
}
