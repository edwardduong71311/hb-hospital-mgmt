package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.Specialty;
import java.util.List;

public interface SpecialtyUseCase {
    List<Specialty> getSpecialties(Pagination pagination);

    Specialty createSpecialty(Specialty spec);

    Specialty updateSpecialty(Specialty spec);

    void deleteSpecialty(String id);
}
