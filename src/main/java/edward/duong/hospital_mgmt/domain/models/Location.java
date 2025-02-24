package edward.duong.hospital_mgmt.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private Double longitude;
    private Double latitude;
}
