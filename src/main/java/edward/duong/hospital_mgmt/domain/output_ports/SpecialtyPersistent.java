package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import java.util.List;

public interface SpecialtyPersistent {
    List<Specialty> getSpecialties(SpecialtyCriteria criteria, Pagination pagination);

    Specialty getSpecialtyByCriteria(SpecialtyCriteria criteria);

    Specialty createSpecialty(Specialty hospital);

    Specialty updateSpecialty(Specialty hospital);
}
