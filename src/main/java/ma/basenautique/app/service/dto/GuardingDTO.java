package ma.basenautique.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ma.basenautique.app.domain.Guarding} entity.
 */
public class GuardingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numberGuarding;

    private String titleGuarding;

    private String descGuarding;


    private Long membershipId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberGuarding() {
        return numberGuarding;
    }

    public void setNumberGuarding(String numberGuarding) {
        this.numberGuarding = numberGuarding;
    }

    public String getTitleGuarding() {
        return titleGuarding;
    }

    public void setTitleGuarding(String titleGuarding) {
        this.titleGuarding = titleGuarding;
    }

    public String getDescGuarding() {
        return descGuarding;
    }

    public void setDescGuarding(String descGuarding) {
        this.descGuarding = descGuarding;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuardingDTO)) {
            return false;
        }

        return id != null && id.equals(((GuardingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuardingDTO{" +
            "id=" + getId() +
            ", numberGuarding='" + getNumberGuarding() + "'" +
            ", titleGuarding='" + getTitleGuarding() + "'" +
            ", descGuarding='" + getDescGuarding() + "'" +
            ", membershipId=" + getMembershipId() +
            "}";
    }
}
