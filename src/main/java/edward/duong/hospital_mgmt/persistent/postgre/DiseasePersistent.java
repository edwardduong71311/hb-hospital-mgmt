package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Disease;
import edward.duong.hospital_mgmt.domain.models.spec.DiseaseCriteria;
import edward.duong.hospital_mgmt.persistent.mapper.DiseaseMapper;
import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseEntity;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresDiseaseRepo;
import edward.duong.hospital_mgmt.persistent.postgre.specification.DiseaseSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class DiseasePersistent implements edward.duong.hospital_mgmt.domain.output_ports.DiseasePersistent {
    private final PostgresDiseaseRepo repo;

    public DiseasePersistent(PostgresDiseaseRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Disease> getDiseases(Pagination pagination) {
        Page<DiseaseEntity> diseases =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return DiseaseMapper.INSTANCE.entityToModels(diseases.getContent());
    }

    @Override
    public Disease getDiseaseByCriteria(DiseaseCriteria criteria) {
        Specification<DiseaseEntity> spec = DiseaseSpecification.filterByCriteria(criteria);
        return DiseaseMapper.INSTANCE.toModel(repo.findOne(spec).orElse(null));
    }

    @Override
    public Disease createDisease(Disease disease) {
        disease.setId(null);

        DiseaseEntity toSave = DiseaseMapper.INSTANCE.toSaveEntity(disease);
        toSave.addSpecialties(disease.getSpecialties());

        DiseaseEntity saved = repo.save(toSave);
        return DiseaseMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Disease updateDisease(Disease disease) {
        DiseaseEntity toUpdate = DiseaseMapper.INSTANCE.toUpdateEntity(disease);
        toUpdate.addSpecialties(disease.getSpecialties());

        DiseaseEntity saved = repo.save(toUpdate);
        return DiseaseMapper.INSTANCE.toModel(saved);
    }
}
