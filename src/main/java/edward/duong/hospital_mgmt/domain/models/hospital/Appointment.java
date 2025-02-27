package edward.duong.hospital_mgmt.domain.models.hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Appointment {
    private String id;
    private String patient;
}
