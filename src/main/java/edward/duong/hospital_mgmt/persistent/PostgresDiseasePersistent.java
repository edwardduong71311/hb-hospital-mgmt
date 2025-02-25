package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.models.Disease;
import edward.duong.hospital_mgmt.domain.models.DiseaseCriteria;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.output_ports.DiseasePersistent;
import edward.duong.hospital_mgmt.persistent.mapper.DiseaseMapper;
import edward.duong.hospital_mgmt.persistent.postgre.PostgresDiseaseRepo;
import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseEntity;
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
public class PostgresDiseasePersistent implements DiseasePersistent {
    private final EntityManager entityManager;
    private final PostgresDiseaseRepo repo;

    public PostgresDiseasePersistent(EntityManager entityManager, PostgresDiseaseRepo repo) {
        this.entityManager = entityManager;
        this.repo = repo;
    }

    private CriteriaQuery<DiseaseEntity> buildCriteria(DiseaseCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DiseaseEntity> query = cb.createQuery(DiseaseEntity.class);
        Root<DiseaseEntity> hospitalRoot = query.from(DiseaseEntity.class);

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
    public List<Disease> getDiseases(Pagination pagination) {
        Page<DiseaseEntity> diseases =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return DiseaseMapper.INSTANCE.entityToModels(diseases.getContent());
    }

    @Override
    public Disease getDiseaseByCriteria(DiseaseCriteria criteria) {
        CriteriaQuery<DiseaseEntity> query = buildCriteria(criteria);

        TypedQuery<DiseaseEntity> typedQuery = entityManager.createQuery(query);
        List<DiseaseEntity> data = typedQuery.getResultList();
        if (data.isEmpty()) {
            return null;
        }

        return DiseaseMapper.INSTANCE.toModel(data.getFirst());
    }

    @Override
    public Disease createDisease(Disease disease) {
        disease.setId(null);
        DiseaseEntity saved = repo.save(DiseaseMapper.INSTANCE.toSaveEntity(disease));
        return DiseaseMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Disease updateDisease(Disease disease) {
        DiseaseEntity saved = repo.save(DiseaseMapper.INSTANCE.toUpdateEntity(disease));
        return DiseaseMapper.INSTANCE.toModel(saved);
    }
}
