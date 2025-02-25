package edward.duong.hospital_mgmt.domain.models;

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
}
