package edward.duong.hospital_mgmt.domain.models.hospital;

import edward.duong.hospital_mgmt.domain.models.Location;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hospital {
    private String id;
    private String name;
    private String status;
    private String address;
    private String telephone;
    private Location location;
    private List<Specialist> specialists;
}
