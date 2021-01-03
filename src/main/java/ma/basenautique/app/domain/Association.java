package ma.basenautique.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Association.
 */
@Entity
@Table(name = "association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "association")
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_association", nullable = false, unique = true)
    private String numberAssociation;

    @NotNull
    @Column(name = "name_association", nullable = false)
    private String nameAssociation;

    @Lob
    @Column(name = "image_association")
    private byte[] imageAssociation;

    @Column(name = "image_association_content_type")
    private String imageAssociationContentType;

    @Column(name = "desc_association")
    private String descAssociation;

    @OneToMany(mappedBy = "association")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Member> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberAssociation() {
        return numberAssociation;
    }

    public Association numberAssociation(String numberAssociation) {
        this.numberAssociation = numberAssociation;
        return this;
    }

    public void setNumberAssociation(String numberAssociation) {
        this.numberAssociation = numberAssociation;
    }

    public String getNameAssociation() {
        return nameAssociation;
    }

    public Association nameAssociation(String nameAssociation) {
        this.nameAssociation = nameAssociation;
        return this;
    }

    public void setNameAssociation(String nameAssociation) {
        this.nameAssociation = nameAssociation;
    }

    public byte[] getImageAssociation() {
        return imageAssociation;
    }

    public Association imageAssociation(byte[] imageAssociation) {
        this.imageAssociation = imageAssociation;
        return this;
    }

    public void setImageAssociation(byte[] imageAssociation) {
        this.imageAssociation = imageAssociation;
    }

    public String getImageAssociationContentType() {
        return imageAssociationContentType;
    }

    public Association imageAssociationContentType(String imageAssociationContentType) {
        this.imageAssociationContentType = imageAssociationContentType;
        return this;
    }

    public void setImageAssociationContentType(String imageAssociationContentType) {
        this.imageAssociationContentType = imageAssociationContentType;
    }

    public String getDescAssociation() {
        return descAssociation;
    }

    public Association descAssociation(String descAssociation) {
        this.descAssociation = descAssociation;
        return this;
    }

    public void setDescAssociation(String descAssociation) {
        this.descAssociation = descAssociation;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Association members(Set<Member> members) {
        this.members = members;
        return this;
    }

    public Association addMember(Member member) {
        this.members.add(member);
        member.setAssociation(this);
        return this;
    }

    public Association removeMember(Member member) {
        this.members.remove(member);
        member.setAssociation(null);
        return this;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Association)) {
            return false;
        }
        return id != null && id.equals(((Association) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Association{" +
            "id=" + getId() +
            ", numberAssociation='" + getNumberAssociation() + "'" +
            ", nameAssociation='" + getNameAssociation() + "'" +
            ", imageAssociation='" + getImageAssociation() + "'" +
            ", imageAssociationContentType='" + getImageAssociationContentType() + "'" +
            ", descAssociation='" + getDescAssociation() + "'" +
            "}";
    }
}
