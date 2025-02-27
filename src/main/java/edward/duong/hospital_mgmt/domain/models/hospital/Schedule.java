package edward.duong.hospital_mgmt.domain.models.hospital;

import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {
    private String id;
    private LocalDateTime date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private Hospital hospital;
    private Specialist specialist;
    private Appointment appointment;
}
