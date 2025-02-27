package edward.duong.hospital_mgmt.domain.models.spec;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Specialty {
    private String id;
    private String name;
    private String description;
    private String status;
}
