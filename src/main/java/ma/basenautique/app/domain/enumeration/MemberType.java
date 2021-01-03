package ma.basenautique.app.domain.enumeration;

/**
 * The MemberType enumeration.
 */
public enum MemberType {
    MEMBER_INDIVIDUAL("member_individual"),
    MEMBER_VEHICLEOWNER("member_vehicleowner"),
    MEMBER_GUEST("member_guest");

    private final String value;


    MemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
