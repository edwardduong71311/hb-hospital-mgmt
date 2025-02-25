package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Disease;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import java.util.List;

public interface DiseaseUseCase {
    List<Disease> getDiseases(Pagination pagination);

    Disease createDisease(Disease spec);

    Disease updateDisease(Disease spec);

    void deleteDisease(String id);
}
