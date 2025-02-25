package edward.duong.hospital_mgmt.controller.models.specialist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistRes {
    private String id;
    private String name;
    private String status;
}
