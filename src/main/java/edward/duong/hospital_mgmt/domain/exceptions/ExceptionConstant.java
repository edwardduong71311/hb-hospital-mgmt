package edward.duong.hospital_mgmt.domain.exceptions;

public class ExceptionConstant {
    private ExceptionConstant() {}

    public static final String REQUIRE_HOSPITAL = "Hospital must not be null";
    public static final String REQUIRE_HOSPITAL_ID = "Hospital id must not be null";
    public static final String REQUIRE_HOSPITAL_NAME = "Hospital name must not be null";
    public static final String REQUIRE_HOSPITAL_LOCATION = "Hospital location must not be null";
    public static final String DUPLICATE_HOSPITAL = "Hospital name and location existed";
    public static final String NOTFOUND_HOSPITAL = "Hospital not found";
}
