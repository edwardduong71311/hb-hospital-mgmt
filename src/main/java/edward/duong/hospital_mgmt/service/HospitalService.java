package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        log.info("Hospital creation: {}", hospital);
        return hospitalUseCase.createHospital(hospital);
    }

    public Hospital updateHospital(Hospital hospital) {
        return hospitalUseCase.updateHospital(hospital);
    }

    public void deleteHospital(String id) {
        hospitalUseCase.deleteHospital(id);
    }
}
