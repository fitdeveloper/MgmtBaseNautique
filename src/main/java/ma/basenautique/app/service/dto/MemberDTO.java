package ma.basenautique.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import ma.basenautique.app.domain.enumeration.MemberType;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Member} entity.
 */
public class MemberDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberMember;

    @NotNull
    private MemberType typeMember;

    @NotNull
    private String cinMember;

    private String firstnameMember;

    private String lastnameMember;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String emailMember;

    private String numberPhoneMember;

    @NotNull
    private LocalDate dobMember;

    private String adressMember;

    @Lob
    private byte[] imageMember;

    private String imageMemberContentType;
    private String descMember;


    private Long parentId;

    private Long associationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberMember() {
        return numberMember;
    }

    public void setNumberMember(String numberMember) {
        this.numberMember = numberMember;
    }

    public MemberType getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(MemberType typeMember) {
        this.typeMember = typeMember;
    }

    public String getCinMember() {
        return cinMember;
    }

    public void setCinMember(String cinMember) {
        this.cinMember = cinMember;
    }

    public String getFirstnameMember() {
        return firstnameMember;
    }

    public void setFirstnameMember(String firstnameMember) {
        this.firstnameMember = firstnameMember;
    }

    public String getLastnameMember() {
        return lastnameMember;
    }

    public void setLastnameMember(String lastnameMember) {
        this.lastnameMember = lastnameMember;
    }

    public String getEmailMember() {
        return emailMember;
    }

    public void setEmailMember(String emailMember) {
        this.emailMember = emailMember;
    }

    public String getNumberPhoneMember() {
        return numberPhoneMember;
    }

    public void setNumberPhoneMember(String numberPhoneMember) {
        this.numberPhoneMember = numberPhoneMember;
    }

    public LocalDate getDobMember() {
        return dobMember;
    }

    public void setDobMember(LocalDate dobMember) {
        this.dobMember = dobMember;
    }

    public String getAdressMember() {
        return adressMember;
    }

    public void setAdressMember(String adressMember) {
        this.adressMember = adressMember;
    }

    public byte[] getImageMember() {
        return imageMember;
    }

    public void setImageMember(byte[] imageMember) {
        this.imageMember = imageMember;
    }

    public String getImageMemberContentType() {
        return imageMemberContentType;
    }

    public void setImageMemberContentType(String imageMemberContentType) {
        this.imageMemberContentType = imageMemberContentType;
    }

    public String getDescMember() {
        return descMember;
    }

    public void setDescMember(String descMember) {
        this.descMember = descMember;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long memberId) {
        this.parentId = memberId;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberDTO)) {
            return false;
        }

        return id != null && id.equals(((MemberDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberDTO{" +
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
            ", descMember='" + getDescMember() + "'" +
            ", parentId=" + getParentId() +
            ", associationId=" + getAssociationId() +
            "}";
    }
}
