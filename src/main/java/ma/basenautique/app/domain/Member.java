package ma.basenautique.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ma.basenautique.app.domain.enumeration.MemberType;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_member", nullable = false, unique = true)
    private String numberMember;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_member", nullable = false)
    private MemberType typeMember;

    @NotNull
    @Column(name = "cin_member", nullable = false)
    private String cinMember;

    @Column(name = "firstname_member")
    private String firstnameMember;

    @Column(name = "lastname_member")
    private String lastnameMember;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "email_member", nullable = false, unique = true)
    private String emailMember;

    @Column(name = "number_phone_member")
    private String numberPhoneMember;

    @NotNull
    @Column(name = "dob_member", nullable = false)
    private LocalDate dobMember;

    @Column(name = "adress_member")
    private String adressMember;

    @Lob
    @Column(name = "image_member")
    private byte[] imageMember;

    @Column(name = "image_member_content_type")
    private String imageMemberContentType;

    @Column(name = "desc_member")
    private String descMember;

    @OneToOne
    @JoinColumn(unique = true)
    private Member parent;

    @OneToMany(mappedBy = "member")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Membership> memberships = new HashSet<>();

    @OneToOne(mappedBy = "parent")
    @JsonIgnore
    private Member child;

    @ManyToOne
    @JsonIgnoreProperties(value = "members", allowSetters = true)
    private Association association;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMember() {
        return numberMember;
    }

    public Member numberMember(String numberMember) {
        this.numberMember = numberMember;
        return this;
    }

    public void setNumberMember(String numberMember) {
        this.numberMember = numberMember;
    }

    public MemberType getTypeMember() {
        return typeMember;
    }

    public Member typeMember(MemberType typeMember) {
        this.typeMember = typeMember;
        return this;
    }

    public void setTypeMember(MemberType typeMember) {
        this.typeMember = typeMember;
    }

    public String getCinMember() {
        return cinMember;
    }

    public Member cinMember(String cinMember) {
        this.cinMember = cinMember;
        return this;
    }

    public void setCinMember(String cinMember) {
        this.cinMember = cinMember;
    }

    public String getFirstnameMember() {
        return firstnameMember;
    }

    public Member firstnameMember(String firstnameMember) {
        this.firstnameMember = firstnameMember;
        return this;
    }

    public void setFirstnameMember(String firstnameMember) {
        this.firstnameMember = firstnameMember;
    }

    public String getLastnameMember() {
        return lastnameMember;
    }

    public Member lastnameMember(String lastnameMember) {
        this.lastnameMember = lastnameMember;
        return this;
    }

    public void setLastnameMember(String lastnameMember) {
        this.lastnameMember = lastnameMember;
    }

    public String getEmailMember() {
        return emailMember;
    }

    public Member emailMember(String emailMember) {
        this.emailMember = emailMember;
        return this;
    }

    public void setEmailMember(String emailMember) {
        this.emailMember = emailMember;
    }

    public String getNumberPhoneMember() {
        return numberPhoneMember;
    }

    public Member numberPhoneMember(String numberPhoneMember) {
        this.numberPhoneMember = numberPhoneMember;
        return this;
    }

    public void setNumberPhoneMember(String numberPhoneMember) {
        this.numberPhoneMember = numberPhoneMember;
    }

    public LocalDate getDobMember() {
        return dobMember;
    }

    public Member dobMember(LocalDate dobMember) {
        this.dobMember = dobMember;
        return this;
    }

    public void setDobMember(LocalDate dobMember) {
        this.dobMember = dobMember;
    }

    public String getAdressMember() {
        return adressMember;
    }

    public Member adressMember(String adressMember) {
        this.adressMember = adressMember;
        return this;
    }

    public void setAdressMember(String adressMember) {
        this.adressMember = adressMember;
    }

    public byte[] getImageMember() {
        return imageMember;
    }

    public Member imageMember(byte[] imageMember) {
        this.imageMember = imageMember;
        return this;
    }

    public void setImageMember(byte[] imageMember) {
        this.imageMember = imageMember;
    }

    public String getImageMemberContentType() {
        return imageMemberContentType;
    }

    public Member imageMemberContentType(String imageMemberContentType) {
        this.imageMemberContentType = imageMemberContentType;
        return this;
    }

    public void setImageMemberContentType(String imageMemberContentType) {
        this.imageMemberContentType = imageMemberContentType;
    }

    public String getDescMember() {
        return descMember;
    }

    public Member descMember(String descMember) {
        this.descMember = descMember;
        return this;
    }

    public void setDescMember(String descMember) {
        this.descMember = descMember;
    }

    public Member getParent() {
        return parent;
    }

    public Member parent(Member member) {
        this.parent = member;
        return this;
    }

    public void setParent(Member member) {
        this.parent = member;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }

    public Member memberships(Set<Membership> memberships) {
        this.memberships = memberships;
        return this;
    }

    public Member addMembership(Membership membership) {
        this.memberships.add(membership);
        membership.setMember(this);
        return this;
    }

    public Member removeMembership(Membership membership) {
        this.memberships.remove(membership);
        membership.setMember(null);
        return this;
    }

    public void setMemberships(Set<Membership> memberships) {
        this.memberships = memberships;
    }

    public Member getChild() {
        return child;
    }

    public Member child(Member member) {
        this.child = member;
        return this;
    }

    public void setChild(Member member) {
        this.child = member;
    }

    public Association getAssociation() {
        return association;
    }

    public Member association(Association association) {
        this.association = association;
        return this;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", numberMember='" + getNumberMember() + "'" +
            ", typeMember='" + getTypeMember() + "'" +
            ", cinMember='" + getCinMember() + "'" +
            ", firstnameMember='" + getFirstnameMember() + "'" +
            ", lastnameMember='" + getLastnameMember() + "'" +
            ", emailMember='" + getEmailMember() + "'" +
            ", numberPhoneMember='" + getNumberPhoneMember() + "'" +
            ", dobMember='" + getDobMember() + "'" +
            ", adressMember='" + getAdressMember() + "'" +
            ", imageMember='" + getImageMember() + "'" +
            ", imageMemberContentType='" + getImageMemberContentType() + "'" +
            ", descMember='" + getDescMember() + "'" +
            "}";
    }
}
