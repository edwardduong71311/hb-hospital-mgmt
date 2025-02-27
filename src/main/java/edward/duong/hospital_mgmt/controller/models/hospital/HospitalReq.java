package edward.duong.hospital_mgmt.controller.models.hospital;

import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import java.util.List;
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
    private List<SpecialistReq> specialists;
}
