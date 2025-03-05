package edward.duong.hospital_mgmt.domain;

import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import edward.duong.hospital_mgmt.domain.models.hospital.HospitalCriteria;
import edward.duong.hospital_mgmt.domain.models.hospital.HospitalStatus;
import edward.duong.hospital_mgmt.domain.output_ports.HospitalPersistent;
import java.util.List;
import java.util.Objects;

public class DefaultHospitalUsecase implements HospitalUseCase {
    private final HospitalPersistent hospitalPersistent;

    public DefaultHospitalUsecase(HospitalPersistent hospitalPersistent) {
        this.hospitalPersistent = hospitalPersistent;
    }

    @Override
    public List<Hospital> getHospital(Pagination pagination) {
        return hospitalPersistent.getHospitals(pagination);
    }

    @Override
    public Hospital getHospitalById(String id) {
        return hospitalPersistent.getHospitalByCriteria(
                HospitalCriteria.builder().id(id).build());
    }

    private void validateHospitalMandatoryFields(Hospital hospital) {
        if (Objects.isNull(hospital)) {
            throw new IllegalArgumentException(REQUIRE_HOSPITAL);
        }
        if (Objects.isNull(hospital.getName()) || hospital.getName().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_HOSPITAL_NAME);
        }
        if (Objects.isNull(hospital.getLocation())
                || Objects.isNull(hospital.getLocation().getLongitude())
                || Objects.isNull(hospital.getLocation().getLatitude())) {
            throw new IllegalArgumentException(REQUIRE_HOSPITAL_LOCATION);
        }
    }

    @Override
    public Hospital createHospital(Hospital hospital) {
        validateHospitalMandatoryFields(hospital);

        Hospital saved = hospitalPersistent.getHospitalByCriteria(HospitalCriteria.builder()
                .name(hospital.getName())
                .location(hospital.getLocation())
                .build());
        if (Objects.nonNull(saved)) {
            return saved;
        }

        hospital.setStatus(HospitalStatus.ACTIVE.name());
        return hospitalPersistent.createHospital(hospital);
    }

    @Override
    public Hospital updateHospital(Hospital hospital) {
        isHospitalExist(hospital.getId());
        return hospitalPersistent.updateHospital(hospital);
    }

    @Override
    public void deleteHospital(String id) {
        Hospital saved = hospitalPersistent.getHospitalByCriteria(
                HospitalCriteria.builder().id(id).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_HOSPITAL);
        }

        saved.setStatus(HospitalStatus.INACTIVE.name());
        hospitalPersistent.updateHospital(saved);
    }

    @Override
    public void isHospitalExist(String id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(REQUIRE_HOSPITAL_ID);
        }

        Hospital saved = hospitalPersistent.getHospitalByCriteria(
                HospitalCriteria.builder().id(id).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_HOSPITAL);
        }
    }
}
