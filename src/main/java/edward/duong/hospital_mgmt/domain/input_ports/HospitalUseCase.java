package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import java.util.List;

public interface HospitalUseCase {
    List<Hospital> getHospital(Pagination pagination);

    Hospital getHospitalById(String id);

    Hospital createHospital(Hospital hospital);

    Hospital updateHospital(Hospital hospital);

    void deleteHospital(String id);
}
