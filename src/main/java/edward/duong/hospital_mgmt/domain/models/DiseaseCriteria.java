package edward.duong.hospital_mgmt.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiseaseCriteria {
    private String id;
    private String name;
}
