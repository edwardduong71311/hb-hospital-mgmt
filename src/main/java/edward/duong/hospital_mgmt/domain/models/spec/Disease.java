package edward.duong.hospital_mgmt.domain.models.spec;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Disease {
    private String id;
    private String name;
    private String status;
    private String symptom;
    private String treatment;
    private List<Specialty> specialties;
}
