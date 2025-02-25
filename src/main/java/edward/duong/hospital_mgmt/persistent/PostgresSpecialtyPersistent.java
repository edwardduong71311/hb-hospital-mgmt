package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialtyPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialtyMapper;
import edward.duong.hospital_mgmt.persistent.postgre.PostgresSpecialtyRepo;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialtyEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@ConditionalOnBean({PostgresConfig.class})
public class PostgresSpecialtyPersistent implements SpecialtyPersistent {
    private final EntityManager entityManager;
    private final PostgresSpecialtyRepo repo;

    public PostgresSpecialtyPersistent(EntityManager entityManager, PostgresSpecialtyRepo repo) {
        this.entityManager = entityManager;
        this.repo = repo;
    }

    private CriteriaQuery<SpecialtyEntity> buildCriteria(SpecialtyCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialtyEntity> query = cb.createQuery(SpecialtyEntity.class);
        Root<SpecialtyEntity> hospitalRoot = query.from(SpecialtyEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(criteria.getId()) && StringUtils.isNotBlank(criteria.getId())) {
            predicates.add(cb.equal(hospitalRoot.get("id"), Integer.parseInt(criteria.getId())));
        }
        if (Objects.nonNull(criteria.getName()) && StringUtils.isNotBlank(criteria.getName())) {
            predicates.add(cb.equal(hospitalRoot.get("name"), criteria.getName()));
        }
        query.where(predicates.toArray(new Predicate[0]));
        return query;
    }

    @Override
    public List<Specialty> getSpecialties(Pagination pagination) {
        Page<SpecialtyEntity> specialties =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialtyMapper.INSTANCE.entityToModels(specialties.getContent());
    }

    @Override
    public Specialty getSpecialtyByCriteria(SpecialtyCriteria criteria) {
        CriteriaQuery<SpecialtyEntity> query = buildCriteria(criteria);

        TypedQuery<SpecialtyEntity> typedQuery = entityManager.createQuery(query);
        List<SpecialtyEntity> data = typedQuery.getResultList();
        if (data.isEmpty()) {
            return null;
        }

        return SpecialtyMapper.INSTANCE.toModel(data.getFirst());
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
