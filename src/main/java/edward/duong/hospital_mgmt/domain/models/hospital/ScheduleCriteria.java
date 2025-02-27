package edward.duong.hospital_mgmt.domain.models.hospital;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleCriteria {
    private String id;
    private String hospitalId;
    private LocalDateTime date;
}
