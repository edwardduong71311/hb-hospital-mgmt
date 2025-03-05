package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.SpecialistUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpecialistService {
    private final SpecialistUseCase specialistUseCase;

    public SpecialistService(SpecialistUseCase specialistUseCase) {
        this.specialistUseCase = specialistUseCase;
    }

    public List<Specialist> getSpecialists(SpecialistCriteria criteria, Pagination pagination) {
        return specialistUseCase.getSpecialists(criteria, pagination);
    }

    public Specialist createSpecialist(Specialist specialist) {
        return specialistUseCase.createSpecialist(specialist);
    }

    public Specialist updateSpecialist(Specialist specialist) {
        return specialistUseCase.updateSpecialist(specialist);
    }

    public void deleteSpecialist(String id) {
        specialistUseCase.deleteSpecialist(id);
    }
}
