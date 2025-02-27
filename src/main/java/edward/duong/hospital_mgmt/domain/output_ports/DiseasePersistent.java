package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.spec.Disease;
import edward.duong.hospital_mgmt.domain.models.spec.DiseaseCriteria;
import java.util.List;

public interface DiseasePersistent {
    List<Disease> getDiseases(Pagination pagination);

    Disease getDiseaseByCriteria(DiseaseCriteria criteria);

    Disease createDisease(Disease hospital);

    Disease updateDisease(Disease hospital);
}
