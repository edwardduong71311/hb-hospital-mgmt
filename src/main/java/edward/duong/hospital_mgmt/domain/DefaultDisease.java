package edward.duong.hospital_mgmt.domain;

import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.domain.input_ports.DiseaseUseCase;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.output_ports.DiseasePersistent;
import java.util.List;
import java.util.Objects;

public class DefaultDisease implements DiseaseUseCase {
    private final DiseasePersistent persistent;

    public DefaultDisease(DiseasePersistent persistent) {
        this.persistent = persistent;
    }

    private void validateMandatoryFields(Disease disease) {
        if (Objects.isNull(disease)) {
            throw new IllegalArgumentException(REQUIRE_DISEASE);
        }
        if (Objects.isNull(disease.getName()) || disease.getName().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_DISEASE_NAME);
        }
    }

    @Override
    public List<Disease> getDiseases(Pagination pagination) {
        return persistent.getDiseases(pagination);
    }

    @Override
    public Disease createDisease(Disease spec) {
        validateMandatoryFields(spec);

        Disease saved = persistent.getDiseaseByCriteria(
                DiseaseCriteria.builder().name(spec.getName()).build());
        if (Objects.nonNull(saved)) {
            throw new IllegalArgumentException(DUPLICATE_DISEASE);
        }

        spec.setStatus(DiseaseStatus.ACTIVE.name());
        return persistent.createDisease(spec);
    }

    @Override
    public Disease updateDisease(Disease spec) {
        if (Objects.isNull(spec.getId()) || spec.getId().isEmpty()) {
            throw new IllegalArgumentException(REQUIRE_DISEASE_ID);
        }
        validateMandatoryFields(spec);

        Disease saved = persistent.getDiseaseByCriteria(
                DiseaseCriteria.builder().id(spec.getId()).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_DISEASE);
        }

        return persistent.updateDisease(spec);
    }

    @Override
    public void deleteDisease(String id) {
        Disease saved =
                persistent.getDiseaseByCriteria(DiseaseCriteria.builder().id(id).build());
        if (Objects.isNull(saved)) {
            throw new IllegalArgumentException(NOTFOUND_DISEASE);
        }

        saved.setStatus(DiseaseStatus.INACTIVE.name());
        persistent.updateDisease(saved);
    }
}
