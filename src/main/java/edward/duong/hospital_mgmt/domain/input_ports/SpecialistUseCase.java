package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;

import java.util.List;

public interface SpecialistUseCase {
    List<Specialist> getSpecialists(SpecialistCriteria criteria, Pagination pagination);

    Specialist createSpecialist(Specialist spec);

    Specialist updateSpecialist(Specialist spec);

    void deleteSpecialist(String id);
}
