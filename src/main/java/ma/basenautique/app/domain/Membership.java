package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "membership")
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_membership", nullable = false, unique = true)
    private String numberMembership;

    @Column(name = "amount_membership", precision = 21, scale = 2)
    private BigDecimal amountMembership;

    @NotNull
    @Column(name = "start_date_membership", nullable = false)
    private LocalDate startDateMembership;

    @NotNull
    @Column(name = "end_date_membership", nullable = false)
    private LocalDate endDateMembership;

    @NotNull
    @Column(name = "valid_membership", nullable = false)
    private Boolean validMembership;

    @Column(name = "desc_membership")
    private String descMembership;

    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Guarding> guardings = new HashSet<>();

    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Doc> docs = new HashSet<>();

    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MedicalCertificate> medicalCertificates = new HashSet<>();

    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Dealership> dealerships = new HashSet<>();

    @OneToMany(mappedBy = "membership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vehicle> vehicles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "memberships", allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMembership() {
        return numberMembership;
    }

    public Membership numberMembership(String numberMembership) {
        this.numberMembership = numberMembership;
        return this;
    }

    public void setNumberMembership(String numberMembership) {
        this.numberMembership = numberMembership;
    }

    public BigDecimal getAmountMembership() {
        return amountMembership;
    }

    public Membership amountMembership(BigDecimal amountMembership) {
        this.amountMembership = amountMembership;
        return this;
    }

    public void setAmountMembership(BigDecimal amountMembership) {
        this.amountMembership = amountMembership;
    }

    public LocalDate getStartDateMembership() {
        return startDateMembership;
    }

    public Membership startDateMembership(LocalDate startDateMembership) {
        this.startDateMembership = startDateMembership;
        return this;
    }

    public void setStartDateMembership(LocalDate startDateMembership) {
        this.startDateMembership = startDateMembership;
    }

    public LocalDate getEndDateMembership() {
        return endDateMembership;
    }

    public Membership endDateMembership(LocalDate endDateMembership) {
        this.endDateMembership = endDateMembership;
        return this;
    }

    public void setEndDateMembership(LocalDate endDateMembership) {
        this.endDateMembership = endDateMembership;
    }

    public Boolean isValidMembership() {
        return validMembership;
    }

    public Membership validMembership(Boolean validMembership) {
        this.validMembership = validMembership;
        return this;
    }

    public void setValidMembership(Boolean validMembership) {
        this.validMembership = validMembership;
    }

    public String getDescMembership() {
        return descMembership;
    }

    public Membership descMembership(String descMembership) {
        this.descMembership = descMembership;
        return this;
    }

    public void setDescMembership(String descMembership) {
        this.descMembership = descMembership;
    }

    public Set<Guarding> getGuardings() {
        return guardings;
    }

    public Membership guardings(Set<Guarding> guardings) {
        this.guardings = guardings;
        return this;
    }

    public Membership addGuarding(Guarding guarding) {
        this.guardings.add(guarding);
        guarding.setMembership(this);
        return this;
    }

    public Membership removeGuarding(Guarding guarding) {
        this.guardings.remove(guarding);
        guarding.setMembership(null);
        return this;
    }

    public void setGuardings(Set<Guarding> guardings) {
        this.guardings = guardings;
    }

    public Set<Doc> getDocs() {
        return docs;
    }

    public Membership docs(Set<Doc> docs) {
        this.docs = docs;
        return this;
    }

    public Membership addDoc(Doc doc) {
        this.docs.add(doc);
        doc.setMembership(this);
        return this;
    }

    public Membership removeDoc(Doc doc) {
        this.docs.remove(doc);
        doc.setMembership(null);
        return this;
    }

    public void setDocs(Set<Doc> docs) {
        this.docs = docs;
    }

    public Set<MedicalCertificate> getMedicalCertificates() {
        return medicalCertificates;
    }

    public Membership medicalCertificates(Set<MedicalCertificate> medicalCertificates) {
        this.medicalCertificates = medicalCertificates;
        return this;
    }

    public Membership addMedicalCertificate(MedicalCertificate medicalCertificate) {
        this.medicalCertificates.add(medicalCertificate);
        medicalCertificate.setMembership(this);
        return this;
    }

    public Membership removeMedicalCertificate(MedicalCertificate medicalCertificate) {
        this.medicalCertificates.remove(medicalCertificate);
        medicalCertificate.setMembership(null);
        return this;
    }

    public void setMedicalCertificates(Set<MedicalCertificate> medicalCertificates) {
        this.medicalCertificates = medicalCertificates;
    }

    public Set<Dealership> getDealerships() {
        return dealerships;
    }

    public Membership dealerships(Set<Dealership> dealerships) {
        this.dealerships = dealerships;
        return this;
    }

    public Membership addDealership(Dealership dealership) {
        this.dealerships.add(dealership);
        dealership.setMembership(this);
        return this;
    }

    public Membership removeDealership(Dealership dealership) {
        this.dealerships.remove(dealership);
        dealership.setMembership(null);
        return this;
    }

    public void setDealerships(Set<Dealership> dealerships) {
        this.dealerships = dealerships;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public Membership vehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Membership addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setMembership(this);
        return this;
    }

    public Membership removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setMembership(null);
        return this;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Member getMember() {
        return member;
    }

    public Membership member(Member member) {
        this.member = member;
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Membership)) {
            return false;
        }
        return id != null && id.equals(((Membership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            ", numberMembership='" + getNumberMembership() + "'" +
            ", amountMembership=" + getAmountMembership() +
            ", startDateMembership='" + getStartDateMembership() + "'" +
            ", endDateMembership='" + getEndDateMembership() + "'" +
            ", validMembership='" + isValidMembership() + "'" +
            ", descMembership='" + getDescMembership() + "'" +
            "}";
    }
}
