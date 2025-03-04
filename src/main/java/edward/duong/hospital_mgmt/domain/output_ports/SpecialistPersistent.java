package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;
import java.util.List;

public interface SpecialistPersistent {
    List<Specialist> getSpecialists(SpecialistCriteria criteria, Pagination pagination);

    Specialist getSpecialistByCriteria(SpecialistCriteria criteria);

    Specialist createSpecialist(Specialist hospital);

    Specialist updateSpecialist(Specialist hospital);
}
