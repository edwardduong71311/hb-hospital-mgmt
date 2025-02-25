package edward.duong.hospital_mgmt.controller.models.disease;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseRes {
    private String id;
    private String name;
    private String status;
    private String symptom;
    private String treatment;
}
