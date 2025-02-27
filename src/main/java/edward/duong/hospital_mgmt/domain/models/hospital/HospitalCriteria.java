package edward.duong.hospital_mgmt.domain.models.hospital;

import edward.duong.hospital_mgmt.domain.models.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalCriteria {
    private String id;
    private String name;
    private Location location;
    private int radius = 10;
}
