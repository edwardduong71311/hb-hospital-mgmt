package edward.duong.hospital_mgmt.controller.models.specialty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyRes {
    private String id;
    private String name;
    private String description;
}
