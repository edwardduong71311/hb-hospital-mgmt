package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.SpecialtyUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpecialtyService {
    private final SpecialtyUseCase specialtyUseCase;

    public SpecialtyService(SpecialtyUseCase specialtyUseCase) {
        this.specialtyUseCase = specialtyUseCase;
    }

    public List<Specialty> getSpecialties(SpecialtyCriteria criteria, Pagination pagination) {
        return specialtyUseCase.getSpecialties(criteria, pagination);
    }

    public Specialty createSpecialty(Specialty specialty) {
        return specialtyUseCase.createSpecialty(specialty);
    }

    public Specialty updateSpecialty(Specialty specialty) {
        return specialtyUseCase.updateSpecialty(specialty);
    }

    public void deleteSpecialty(String id) {
        specialtyUseCase.deleteSpecialty(id);
    }
}
