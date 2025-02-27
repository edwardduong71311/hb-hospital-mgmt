package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HospitalService {
    private final HospitalUseCase hospitalUseCase;

    public HospitalService(HospitalUseCase hospitalUseCase) {
        this.hospitalUseCase = hospitalUseCase;
    }

    public List<Hospital> getHospital(Pagination pagination) {
        return hospitalUseCase.getHospital(pagination);
    }

    public Hospital getHospitalById(String id) {
        return hospitalUseCase.getHospitalById(id);
    }

    public Hospital createHospital(Hospital hospital) {
        return hospitalUseCase.createHospital(hospital);
    }

    public Hospital updateHospital(Hospital hospital) {
        return hospitalUseCase.updateHospital(hospital);
    }

    public void deleteHospital(String id) {
        hospitalUseCase.deleteHospital(id);
    }
}
