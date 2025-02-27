package edward.duong.hospital_mgmt.controller.models.specialist;

import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistReq {
    private String id;
    private String name;
    private String status;
    private List<SpecialtyReq> specialties;
}
