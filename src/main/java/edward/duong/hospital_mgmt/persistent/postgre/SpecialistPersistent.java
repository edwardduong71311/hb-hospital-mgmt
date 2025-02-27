package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialistMapper;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresSpecialistRepo;
import edward.duong.hospital_mgmt.persistent.postgre.specification.SpecialistSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SpecialistPersistent implements edward.duong.hospital_mgmt.domain.output_ports.SpecialistPersistent {
    private final PostgresSpecialistRepo repo;

    public SpecialistPersistent(PostgresSpecialistRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Specialist> getSpecialists(Pagination pagination) {
        Page<SpecialistEntity> specialists =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialistMapper.INSTANCE.entityToModels(specialists.getContent());
    }

    @Override
    public Specialist getSpecialistByCriteria(SpecialistCriteria criteria) {
        Specification<SpecialistEntity> spec = SpecialistSpecification.filterByCriteria(criteria);
        return SpecialistMapper.INSTANCE.toModel(repo.findOne(spec).orElse(null));
    }

    @Override
    public Specialist createSpecialist(Specialist specialist) {
        specialist.setId(null);

        SpecialistEntity toSave = SpecialistMapper.INSTANCE.toSaveEntity(specialist);
        toSave.addSpecialties(specialist.getSpecialties());

        SpecialistEntity saved = repo.save(toSave);
        return SpecialistMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Specialist updateSpecialist(Specialist specialist) {
        SpecialistEntity toUpdated = SpecialistMapper.INSTANCE.toUpdateEntity(specialist);
        toUpdated.addSpecialties(specialist.getSpecialties());

        SpecialistEntity saved = repo.save(toUpdated);
        return SpecialistMapper.INSTANCE.toModel(saved);
    }
}
