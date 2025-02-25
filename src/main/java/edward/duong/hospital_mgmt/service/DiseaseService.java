package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.DiseaseUseCase;
import edward.duong.hospital_mgmt.domain.models.Disease;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiseaseService {
    private final DiseaseUseCase diseaseUseCase;

    public DiseaseService(DiseaseUseCase diseaseUseCase) {
        this.diseaseUseCase = diseaseUseCase;
    }

    public List<Disease> getDiseases(Pagination pagination) {
        return diseaseUseCase.getDiseases(pagination);
    }

    public Disease createDisease(Disease disease) {
        return diseaseUseCase.createDisease(disease);
    }

    public Disease updateDisease(Disease disease) {
        return diseaseUseCase.updateDisease(disease);
    }

    public void deleteDisease(String id) {
        diseaseUseCase.deleteDisease(id);
    }
}
