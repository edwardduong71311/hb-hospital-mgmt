package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialtyMapper;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialtyEntity;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresSpecialtyRepo;
import edward.duong.hospital_mgmt.persistent.postgre.specification.SpecialtySpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SpecialtyPersistent implements edward.duong.hospital_mgmt.domain.output_ports.SpecialtyPersistent {
    private final PostgresSpecialtyRepo repo;

    public SpecialtyPersistent(PostgresSpecialtyRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Specialty> getSpecialties(SpecialtyCriteria criteria, Pagination pagination) {
        Specification<SpecialtyEntity> spec = SpecialtySpecification.filterByCriteria(criteria);

        Page<SpecialtyEntity> specialties =
                repo.findAll(spec, Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialtyMapper.INSTANCE.entityToModels(specialties.getContent());
    }

    @Override
    public Specialty getSpecialtyByCriteria(SpecialtyCriteria criteria) {
        Specification<SpecialtyEntity> spec = SpecialtySpecification.filterByCriteria(criteria);
        return SpecialtyMapper.INSTANCE.toModel(repo.findOne(spec).orElse(null));
    }

    @Override
    public Specialty createSpecialty(Specialty specialty) {
        specialty.setId(null);
        SpecialtyEntity saved = repo.save(SpecialtyMapper.INSTANCE.toSaveEntity(specialty));
        return SpecialtyMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Specialty updateSpecialty(Specialty specialty) {
        SpecialtyEntity saved = repo.save(SpecialtyMapper.INSTANCE.toUpdateEntity(specialty));
        return SpecialtyMapper.INSTANCE.toModel(saved);
    }
}
