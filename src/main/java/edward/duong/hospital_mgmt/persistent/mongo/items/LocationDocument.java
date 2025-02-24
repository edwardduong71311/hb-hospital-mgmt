package edward.duong.hospital_mgmt.persistent.mongo.items;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDocument {
    private Double longitude;
    private Double latitude;
}
