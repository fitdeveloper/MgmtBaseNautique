package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Guarding.
 */
@Entity
@Table(name = "guarding")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "guarding")
public class Guarding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_guarding", nullable = false, unique = true)
    private String numberGuarding;

    @Column(name = "title_guarding")
    private String titleGuarding;

    @Column(name = "desc_guarding")
    private String descGuarding;

    @ManyToOne
    @JsonIgnoreProperties(value = "guardings", allowSetters = true)
    private Membership membership;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberGuarding() {
        return numberGuarding;
    }

    public Guarding numberGuarding(String numberGuarding) {
        this.numberGuarding = numberGuarding;
        return this;
    }

    public void setNumberGuarding(String numberGuarding) {
        this.numberGuarding = numberGuarding;
    }

    public String getTitleGuarding() {
        return titleGuarding;
    }

    public Guarding titleGuarding(String titleGuarding) {
        this.titleGuarding = titleGuarding;
        return this;
    }

    public void setTitleGuarding(String titleGuarding) {
        this.titleGuarding = titleGuarding;
    }

    public String getDescGuarding() {
        return descGuarding;
    }

    public Guarding descGuarding(String descGuarding) {
        this.descGuarding = descGuarding;
        return this;
    }

    public void setDescGuarding(String descGuarding) {
        this.descGuarding = descGuarding;
    }

    public Membership getMembership() {
        return membership;
    }

    public Guarding membership(Membership membership) {
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
        if (!(o instanceof Guarding)) {
            return false;
        }
        return id != null && id.equals(((Guarding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Guarding{" +
            "id=" + getId() +
            ", numberGuarding='" + getNumberGuarding() + "'" +
            ", titleGuarding='" + getTitleGuarding() + "'" +
            ", descGuarding='" + getDescGuarding() + "'" +
            "}";
    }
}
