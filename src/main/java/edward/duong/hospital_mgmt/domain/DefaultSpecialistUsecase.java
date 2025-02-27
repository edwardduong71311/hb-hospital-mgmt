package edward.duong.hospital_mgmt.domain;

import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.domain.input_ports.SpecialistUseCase;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistStatus;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialistPersistent;
import java.util.List;
import java.util.Objects;

public class DefaultSpecialistUsecase implements SpecialistUseCase {
    private final SpecialistPersistent persistent;

    public DefaultSpecialistUsecase(SpecialistPersistent persistent) {
        this.persistent = persistent;
    }

    private void validateMandatoryFields(Specialist specialist) {
        if (Objects.isNull(specialist)) {
            throw new IllegalArgumentException(REQUIRE_SPECIALIST);
        }
        if (Objects.isNull(specialist.getName()) || specialist.getName().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_SPECIALIST_NAME);
        }
    }

    @Override
    public List<Specialist> getSpecialists(Pagination pagination) {
        return persistent.getSpecialists(pagination);
    }

    @Override
    public Specialist createSpecialist(Specialist spec) {
        validateMandatoryFields(spec);

        Specialist saved = persistent.getSpecialistByCriteria(
                SpecialistCriteria.builder().name(spec.getName()).build());
        if (Objects.nonNull(saved)) {
            throw new IllegalArgumentException(DUPLICATE_SPECIALIST);
        }

        spec.setStatus(SpecialistStatus.ACTIVE.name());
        return persistent.createSpecialist(spec);
    }

    @Override
    public Specialist updateSpecialist(Specialist spec) {
        if (Objects.isNull(spec.getId()) || spec.getId().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_SPECIALIST_ID);
        }
        validateMandatoryFields(spec);

        Specialist saved = persistent.getSpecialistByCriteria(
                SpecialistCriteria.builder().id(spec.getId()).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_SPECIALIST);
        }

        return persistent.updateSpecialist(spec);
    }

    @Override
    public void deleteSpecialist(String id) {
        Specialist saved = persistent.getSpecialistByCriteria(
                SpecialistCriteria.builder().id(id).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_SPECIALIST);
        }

        saved.setStatus(SpecialistStatus.INACTIVE.name());
        persistent.updateSpecialist(saved);
    }
}
