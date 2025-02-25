package edward.duong.hospital_mgmt.controller.models.hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalReq {
    private String id;
    private String name;
    private String status;
    private String address;
    private String telephone;
    private Double longitude;
    private Double latitude;
}
