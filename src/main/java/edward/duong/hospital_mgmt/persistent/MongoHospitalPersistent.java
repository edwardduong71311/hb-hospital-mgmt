package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.HospitalCriteria;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.output_ports.HospitalPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.HospitalMapper;
import edward.duong.hospital_mgmt.persistent.mongo.MongoHospitalRepo;
import edward.duong.hospital_mgmt.persistent.mongo.items.HospitalDocument;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnBean({MongoConfig.class})
public class MongoHospitalPersistent implements HospitalPersistent {
    private final MongoTemplate mongoTemplate;
    private final MongoHospitalRepo repo;

    public MongoHospitalPersistent(MongoTemplate mongoTemplate, MongoHospitalRepo repo) {
        this.mongoTemplate = mongoTemplate;
        this.repo = repo;
    }

    @Override
    public List<Hospital> getHospitals(Pagination pagination) {
        Page<HospitalDocument> hospitals =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return HospitalMapper.INSTANCE.documentToModels(hospitals.getContent());
    }

    @Override
    public List<Hospital> getHospitalsByCriteria(HospitalCriteria criteria, Pagination pagination) {
        Query query = buildCriteria(criteria);

        List<HospitalDocument> hospitals = mongoTemplate.find(query, HospitalDocument.class);
        return HospitalMapper.INSTANCE.documentToModels(hospitals);
    }

    private Query buildCriteria(HospitalCriteria criteria) {
        Query query = new Query();
        Criteria filter = new Criteria();

        if (criteria != null) {
            List<Criteria> criteriaList = new ArrayList<>();
            if (Objects.nonNull(criteria.getId()) && StringUtils.isNotBlank(criteria.getId())) {
                criteriaList.add(Criteria.where("_id").is(criteria.getId()));
            }
            if (Objects.nonNull(criteria.getName()) && StringUtils.isNotBlank(criteria.getName())) {
                criteriaList.add(Criteria.where("name").is(criteria.getName()));
            }
            if (Objects.nonNull(criteria.getLocation())) {
                criteriaList.add(Criteria.where("location.longitude")
                        .is(criteria.getLocation().getLongitude()));
                criteriaList.add(Criteria.where("location.latitude")
                        .is(criteria.getLocation().getLatitude()));
            }
            filter.andOperator(criteriaList);
        }

        query.addCriteria(filter);
        return query;
    }

    @Override
    public Hospital getHospitalByCriteria(HospitalCriteria criteria) {
        Query query = buildCriteria(criteria);

        HospitalDocument hospital = mongoTemplate.findOne(query, HospitalDocument.class);
        return HospitalMapper.INSTANCE.toModel(hospital);
    }

    @Override
    public Hospital createHospital(Hospital hospital) {
        hospital.setId(null);
        HospitalDocument saved = repo.save(HospitalMapper.INSTANCE.toDocument(hospital));
        return HospitalMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Hospital updateHospital(Hospital hospital) {
        HospitalDocument saved = repo.save(HospitalMapper.INSTANCE.toDocument(hospital));
        return HospitalMapper.INSTANCE.toModel(saved);
    }

    @Override
    public void deleteHospital(String id) {
        repo.deleteById(id);
    }
}
