package edward.duong.hospital_mgmt.controller.models.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalRes {
    private String id;
    private String name;
    private String status;
    private String address;
    private String telephone;
    private Double longitude;
    private Double latitude;
}
