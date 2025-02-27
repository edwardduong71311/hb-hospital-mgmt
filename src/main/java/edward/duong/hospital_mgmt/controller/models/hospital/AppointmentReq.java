package edward.duong.hospital_mgmt.controller.models.hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentReq {
    private String patient;
}
