package edward.duong.hospital_mgmt.controller.models.hospital;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleReq {
    private LocalDateTime date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String specialistId;
    private String hospitalId;
}
