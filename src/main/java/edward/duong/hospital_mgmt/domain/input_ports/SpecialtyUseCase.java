package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;

import java.util.List;

public interface SpecialtyUseCase {
    List<Specialty> getSpecialties(SpecialtyCriteria criteria, Pagination pagination);

    Specialty createSpecialty(Specialty spec);

    Specialty updateSpecialty(Specialty spec);

    void deleteSpecialty(String id);
}
