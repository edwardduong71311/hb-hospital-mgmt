package edward.duong.hospital_mgmt.controller.models.hospitals;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalReq {
    private String id;
    private String name;
    private Double longitude;
    private Double latitude;
}
