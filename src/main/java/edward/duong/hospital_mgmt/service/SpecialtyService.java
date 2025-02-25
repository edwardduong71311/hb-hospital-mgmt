package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.SpecialtyUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.Specialty;
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

    public List<Specialty> getSpecialties(Pagination pagination) {
        return specialtyUseCase.getSpecialties(pagination);
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
