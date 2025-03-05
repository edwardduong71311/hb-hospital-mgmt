package edward.duong.hospital_mgmt.domain;

import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.domain.input_ports.SpecialtyUseCase;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyStatus;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialtyPersistent;
import java.util.List;
import java.util.Objects;

public class DefaultSpecialtyUsecase implements SpecialtyUseCase {
    private final SpecialtyPersistent persistent;

    public DefaultSpecialtyUsecase(SpecialtyPersistent persistent) {
        this.persistent = persistent;
    }

    private void validateMandatoryFields(Specialty specialty) {
        if (Objects.isNull(specialty)) {
            throw new IllegalArgumentException(REQUIRE_SPECIALTY);
        }
        if (Objects.isNull(specialty.getName()) || specialty.getName().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_SPECIALTY_NAME);
        }
    }

    @Override
    public List<Specialty> getSpecialties(SpecialtyCriteria criteria, Pagination pagination) {
        return persistent.getSpecialties(criteria, pagination);
    }

    @Override
    public Specialty createSpecialty(Specialty spec) {
        validateMandatoryFields(spec);

        Specialty saved = persistent.getSpecialtyByCriteria(
                SpecialtyCriteria.builder().name(spec.getName()).build());
        if (Objects.nonNull(saved)) {
            return saved;
        }

        spec.setStatus(SpecialtyStatus.ACTIVE.name());
        return persistent.createSpecialty(spec);
    }

    @Override
    public Specialty updateSpecialty(Specialty spec) {
        if (Objects.isNull(spec.getId()) || spec.getId().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_SPECIALTY_ID);
        }
        validateMandatoryFields(spec);

        Specialty saved = persistent.getSpecialtyByCriteria(
                SpecialtyCriteria.builder().id(spec.getId()).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_SPECIALTY);
        }

        return persistent.updateSpecialty(spec);
    }

    @Override
    public void deleteSpecialty(String id) {
        Specialty saved = persistent.getSpecialtyByCriteria(
                SpecialtyCriteria.builder().id(id).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_SPECIALTY);
        }

        saved.setStatus(SpecialtyStatus.INACTIVE.name());
        persistent.updateSpecialty(saved);
    }
}
