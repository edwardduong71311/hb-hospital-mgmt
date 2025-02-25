package edward.duong.hospital_mgmt.persistent.mongo.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Specialties")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialtyDocument {
    @Id
    private String id;

    private String name;
    private String status;
    private String description;
}
