package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import java.util.List;

public interface HospitalUseCase {
    List<Hospital> getHospital(Pagination pagination);

    Hospital getHospitalById(String id);

    Hospital createHospital(Hospital hospital);

    Hospital updateHospital(Hospital hospital);

    void deleteHospital(String id);

    void isHospitalExist(String id);
}
