package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.*;
import java.util.List;

public interface SpecialtyPersistent {
    List<Specialty> getSpecialties(Pagination pagination);

    Specialty getSpecialtyByCriteria(SpecialtyCriteria criteria);

    Specialty createSpecialty(Specialty hospital);

    Specialty updateSpecialty(Specialty hospital);
}
