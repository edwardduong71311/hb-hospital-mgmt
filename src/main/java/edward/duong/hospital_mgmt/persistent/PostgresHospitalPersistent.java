package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.HospitalCriteria;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.output_ports.HospitalPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.HospitalMapper;
import edward.duong.hospital_mgmt.persistent.postgre.PostgresHospitalRepo;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
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
public class PostgresHospitalPersistent implements HospitalPersistent {
    private final EntityManager entityManager;
    private final PostgresHospitalRepo repo;

    public PostgresHospitalPersistent(EntityManager entityManager, PostgresHospitalRepo repo) {
        this.entityManager = entityManager;
        this.repo = repo;
    }

    @Override
    public List<Hospital> getHospitals(Pagination pagination) {
        Page<HospitalEntity> hospitals =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return HospitalMapper.INSTANCE.entityToModels(hospitals.getContent());
    }

    @Override
    public List<Hospital> getHospitalsByCriteria(HospitalCriteria criteria, Pagination pagination) {
        CriteriaQuery<HospitalEntity> query = buildCriteria(criteria);

        TypedQuery<HospitalEntity> typedQuery = entityManager.createQuery(query);
        List<HospitalEntity> data = typedQuery.getResultList();
        if (data.isEmpty()) {
            return Collections.emptyList();
        }

        return HospitalMapper.INSTANCE.entityToModels(data);
    }

    private CriteriaQuery<HospitalEntity> buildCriteria(HospitalCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<HospitalEntity> query = cb.createQuery(HospitalEntity.class);
        Root<HospitalEntity> hospitalRoot = query.from(HospitalEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(criteria.getId()) && StringUtils.isNotBlank(criteria.getId())) {
            predicates.add(cb.equal(hospitalRoot.get("id"), Integer.parseInt(criteria.getId())));
        }
        if (Objects.nonNull(criteria.getName()) && StringUtils.isNotBlank(criteria.getName())) {
            predicates.add(cb.equal(hospitalRoot.get("name"), criteria.getName()));
        }
        if (Objects.nonNull(criteria.getLocation())) {
            predicates.add(cb.isTrue(cb.function(
                    "ST_DWithin",
                    Boolean.class,
                    hospitalRoot.get("location"),
                    cb.function(
                            "ST_SetSRID",
                            Object.class,
                            cb.function(
                                    "ST_MakePoint",
                                    Object.class,
                                    cb.literal(criteria.getLocation().getLongitude()),
                                    cb.literal(criteria.getLocation().getLatitude())),
                            cb.literal(4326)),
                    cb.literal(criteria.getRadius() * 1000) // Convert km to meters
                    )));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return query;
    }

    @Override
    public Hospital getHospitalByCriteria(HospitalCriteria criteria) {
        CriteriaQuery<HospitalEntity> query = buildCriteria(criteria);

        TypedQuery<HospitalEntity> typedQuery = entityManager.createQuery(query);
        List<HospitalEntity> data = typedQuery.getResultList();
        if (data.isEmpty()) {
            return null;
        }

        return HospitalMapper.INSTANCE.toModel(data.getFirst());
    }

    @Override
    public Hospital createHospital(Hospital hospital) {
        hospital.setId(null);
        HospitalEntity saved = repo.save(HospitalMapper.INSTANCE.toSaveEntity(hospital));
        return HospitalMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Hospital updateHospital(Hospital hospital) {
        HospitalEntity saved = repo.save(HospitalMapper.INSTANCE.toUpdateEntity(hospital));
        return HospitalMapper.INSTANCE.toModel(saved);
    }

    @Override
    public void deleteHospital(String id) {
        repo.deleteById(Long.parseLong(id));
    }
}
