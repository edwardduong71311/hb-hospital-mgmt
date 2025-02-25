package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.Specialist;
import edward.duong.hospital_mgmt.domain.models.SpecialistCriteria;
import java.util.List;

public interface SpecialistPersistent {
    List<Specialist> getSpecialists(Pagination pagination);

    Specialist getSpecialistByCriteria(SpecialistCriteria criteria);

    Specialist createSpecialist(Specialist hospital);

    Specialist updateSpecialist(Specialist hospital);
}
