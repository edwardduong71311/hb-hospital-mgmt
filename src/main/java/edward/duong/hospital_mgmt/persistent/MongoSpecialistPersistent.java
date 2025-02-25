package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.Specialist;
import edward.duong.hospital_mgmt.domain.models.SpecialistCriteria;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialistPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialistMapper;
import edward.duong.hospital_mgmt.persistent.mongo.MongoSpecialistRepo;
import edward.duong.hospital_mgmt.persistent.mongo.items.SpecialistDocument;
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
public class MongoSpecialistPersistent implements SpecialistPersistent {
    private final MongoTemplate mongoTemplate;
    private final MongoSpecialistRepo repo;

    public MongoSpecialistPersistent(MongoTemplate mongoTemplate, MongoSpecialistRepo repo) {
        this.mongoTemplate = mongoTemplate;
        this.repo = repo;
    }

    private Query buildCriteria(SpecialistCriteria criteria) {
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
            filter.andOperator(criteriaList);
        }

        query.addCriteria(filter);
        return query;
    }

    @Override
    public List<Specialist> getSpecialists(Pagination pagination) {
        Page<SpecialistDocument> specs =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialistMapper.INSTANCE.documentToModels(specs.getContent());
    }

    @Override
    public Specialist getSpecialistByCriteria(SpecialistCriteria criteria) {
        Query query = buildCriteria(criteria);

        SpecialistDocument spec = mongoTemplate.findOne(query, SpecialistDocument.class);
        return SpecialistMapper.INSTANCE.toModel(spec);
    }

    @Override
    public Specialist createSpecialist(Specialist spec) {
        spec.setId(null);
        SpecialistDocument saved = repo.save(SpecialistMapper.INSTANCE.toDocument(spec));
        return SpecialistMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Specialist updateSpecialist(Specialist spec) {
        SpecialistDocument saved = repo.save(SpecialistMapper.INSTANCE.toDocument(spec));
        return SpecialistMapper.INSTANCE.toModel(saved);
    }
}
