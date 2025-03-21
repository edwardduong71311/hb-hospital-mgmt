package edward.duong.hospital_mgmt.domain.exceptions;

public class ExceptionConstant {
    private ExceptionConstant() {}

    public static final String REQUIRE_HOSPITAL = "Hospital must not be null";
    public static final String REQUIRE_HOSPITAL_ID = "Hospital id must not be null";
    public static final String REQUIRE_HOSPITAL_NAME = "Hospital name must not be null";
    public static final String REQUIRE_HOSPITAL_LOCATION = "Hospital location must not be null";
    public static final String NOTFOUND_HOSPITAL = "Hospital not found";
    public static final String NOTFOUND_SCHEDULE = "Schedule not found";

    public static final String REQUIRE_SPECIALTY = "Specialty must not be null";
    public static final String REQUIRE_SPECIALTY_NAME = "Specialty name must not be null";
    public static final String REQUIRE_SPECIALTY_ID = "Specialty id must not be null";
    public static final String NOTFOUND_SPECIALTY = "Specialty not found";

    public static final String REQUIRE_SPECIALIST = "Specialist must not be null";
    public static final String REQUIRE_SPECIALIST_NAME = "Specialist name must not be null";
    public static final String REQUIRE_SPECIALIST_ID = "Specialist id must not be null";
    public static final String NOTFOUND_SPECIALIST = "Specialist not found";

    public static final String REQUIRE_DISEASE = "Disease must not be null";
    public static final String REQUIRE_DISEASE_NAME = "Disease name must not be null";
    public static final String REQUIRE_DISEASE_ID = "Disease id must not be null";
    public static final String NOTFOUND_DISEASE = "Disease not found";
}
