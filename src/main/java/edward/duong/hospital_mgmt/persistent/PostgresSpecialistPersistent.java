package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.Specialist;
import edward.duong.hospital_mgmt.domain.models.SpecialistCriteria;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialistPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialistMapper;
import edward.duong.hospital_mgmt.persistent.postgre.PostgresSpecialistRepo;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
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
public class PostgresSpecialistPersistent implements SpecialistPersistent {
    private final EntityManager entityManager;
    private final PostgresSpecialistRepo repo;

    public PostgresSpecialistPersistent(EntityManager entityManager, PostgresSpecialistRepo repo) {
        this.entityManager = entityManager;
        this.repo = repo;
    }

    private CriteriaQuery<SpecialistEntity> buildCriteria(SpecialistCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialistEntity> query = cb.createQuery(SpecialistEntity.class);
        Root<SpecialistEntity> hospitalRoot = query.from(SpecialistEntity.class);

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
    public List<Specialist> getSpecialists(Pagination pagination) {
        Page<SpecialistEntity> specialists =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialistMapper.INSTANCE.entityToModels(specialists.getContent());
    }

    @Override
    public Specialist getSpecialistByCriteria(SpecialistCriteria criteria) {
        CriteriaQuery<SpecialistEntity> query = buildCriteria(criteria);

        TypedQuery<SpecialistEntity> typedQuery = entityManager.createQuery(query);
        List<SpecialistEntity> data = typedQuery.getResultList();
        if (data.isEmpty()) {
            return null;
        }

        return SpecialistMapper.INSTANCE.toModel(data.getFirst());
    }

    @Override
    public Specialist createSpecialist(Specialist specialist) {
        specialist.setId(null);
        SpecialistEntity saved = repo.save(SpecialistMapper.INSTANCE.toSaveEntity(specialist));
        return SpecialistMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Specialist updateSpecialist(Specialist specialist) {
        SpecialistEntity saved = repo.save(SpecialistMapper.INSTANCE.toUpdateEntity(specialist));
        return SpecialistMapper.INSTANCE.toModel(saved);
    }
}
