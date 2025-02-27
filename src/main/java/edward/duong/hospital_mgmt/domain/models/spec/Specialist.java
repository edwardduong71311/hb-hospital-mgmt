package edward.duong.hospital_mgmt.domain.models.spec;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Specialist {
    private String id;
    private String name;
    private String status;
    private List<Specialty> specialties;
}
