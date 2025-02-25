package edward.duong.hospital_mgmt.domain.models;

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
}
