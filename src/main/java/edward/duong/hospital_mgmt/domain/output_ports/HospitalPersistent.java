package edward.duong.hospital_mgmt.domain.output_ports;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import edward.duong.hospital_mgmt.domain.models.hospital.HospitalCriteria;
import java.util.List;

public interface HospitalPersistent {
    List<Hospital> getHospitals(Pagination pagination);

    List<Hospital> getHospitalsByCriteria(HospitalCriteria criteria, Pagination pagination);

    Hospital getHospitalByCriteria(HospitalCriteria criteria);

    Hospital createHospital(Hospital hospital);

    Hospital updateHospital(Hospital hospital);
}
