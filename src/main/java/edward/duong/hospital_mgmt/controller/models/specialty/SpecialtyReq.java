package edward.duong.hospital_mgmt.controller.models.specialty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialtyReq {
    private String id;
    private String name;
    private String description;
}
