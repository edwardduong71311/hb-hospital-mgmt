package edward.duong.hospital_mgmt.controller.models.specialist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistReq {
    private String id;
    private String name;
    private String status;
}
