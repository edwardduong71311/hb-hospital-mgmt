package edward.duong.hospital_mgmt.controller.models.disease;

import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiseaseReq {
    private String id;
    private String name;
    private String status;
    private String symptom;
    private String treatment;
    private List<SpecialtyReq> specialties;
}
