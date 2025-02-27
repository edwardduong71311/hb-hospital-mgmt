package edward.duong.hospital_mgmt.controller.models.hospital;

import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRes {
    private String id;
    private LocalDateTime date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private HospitalRes hospital;
    private SpecialistRes specialist;
    private AppointmentRes appointment;
}
