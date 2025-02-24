package edward.duong.hospital_mgmt.domain.models;

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
