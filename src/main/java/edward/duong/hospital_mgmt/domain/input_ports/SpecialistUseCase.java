package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import java.util.List;

public interface SpecialistUseCase {
    List<Specialist> getSpecialists(Pagination pagination);

    Specialist createSpecialist(Specialist spec);

    Specialist updateSpecialist(Specialist spec);

    void deleteSpecialist(String id);
}
