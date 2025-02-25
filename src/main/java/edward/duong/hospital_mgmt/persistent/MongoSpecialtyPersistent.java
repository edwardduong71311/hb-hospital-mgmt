package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.output_ports.SpecialtyPersistent;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialtyMapper;
import edward.duong.hospital_mgmt.persistent.mongo.MongoSpecialtyRepo;
import edward.duong.hospital_mgmt.persistent.mongo.items.SpecialtyDocument;
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
public class MongoSpecialtyPersistent implements SpecialtyPersistent {
    private final MongoTemplate mongoTemplate;
    private final MongoSpecialtyRepo repo;

    public MongoSpecialtyPersistent(MongoTemplate mongoTemplate, MongoSpecialtyRepo repo) {
        this.mongoTemplate = mongoTemplate;
        this.repo = repo;
    }

    private Query buildCriteria(SpecialtyCriteria criteria) {
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
    public List<Specialty> getSpecialties(Pagination pagination) {
        Page<SpecialtyDocument> specs =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return SpecialtyMapper.INSTANCE.documentToModels(specs.getContent());
    }

    @Override
    public Specialty getSpecialtyByCriteria(SpecialtyCriteria criteria) {
        Query query = buildCriteria(criteria);

        SpecialtyDocument spec = mongoTemplate.findOne(query, SpecialtyDocument.class);
        return SpecialtyMapper.INSTANCE.toModel(spec);
    }

    @Override
    public Specialty createSpecialty(Specialty spec) {
        spec.setId(null);
        SpecialtyDocument saved = repo.save(SpecialtyMapper.INSTANCE.toDocument(spec));
        return SpecialtyMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Specialty updateSpecialty(Specialty spec) {
        SpecialtyDocument saved = repo.save(SpecialtyMapper.INSTANCE.toDocument(spec));
        return SpecialtyMapper.INSTANCE.toModel(saved);
    }
}
