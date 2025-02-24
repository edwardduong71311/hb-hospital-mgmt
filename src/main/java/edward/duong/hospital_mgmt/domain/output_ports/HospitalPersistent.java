package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.HospitalCriteria;
import edward.duong.hospital_mgmt.domain.models.Pagination;

import java.util.List;

public interface HospitalPersistent {
    List<Hospital> getHospitals(Pagination pagination);
    List<Hospital> getHospitalsByCriteria(HospitalCriteria criteria, Pagination pagination);
    Hospital getHospitalByCriteria(HospitalCriteria criteria);
    Hospital createHospital(Hospital hospital);
    Hospital updateHospital(Hospital hospital);
    void deleteHospital(String id);
}
