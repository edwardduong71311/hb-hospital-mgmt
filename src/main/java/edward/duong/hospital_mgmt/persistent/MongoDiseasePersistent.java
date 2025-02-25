package edward.duong.hospital_mgmt.persistent;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.domain.models.Disease;
import edward.duong.hospital_mgmt.domain.models.DiseaseCriteria;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.output_ports.DiseasePersistent;
import edward.duong.hospital_mgmt.persistent.mapper.DiseaseMapper;
import edward.duong.hospital_mgmt.persistent.mongo.MongoDiseaseRepo;
import edward.duong.hospital_mgmt.persistent.mongo.items.DiseaseDocument;
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
public class MongoDiseasePersistent implements DiseasePersistent {
    private final MongoTemplate mongoTemplate;
    private final MongoDiseaseRepo repo;

    public MongoDiseasePersistent(MongoTemplate mongoTemplate, MongoDiseaseRepo repo) {
        this.mongoTemplate = mongoTemplate;
        this.repo = repo;
    }

    private Query buildCriteria(DiseaseCriteria criteria) {
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
    public List<Disease> getDiseases(Pagination pagination) {
        Page<DiseaseDocument> diseases =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return DiseaseMapper.INSTANCE.documentToModels(diseases.getContent());
    }

    @Override
    public Disease getDiseaseByCriteria(DiseaseCriteria criteria) {
        Query query = buildCriteria(criteria);

        DiseaseDocument disease = mongoTemplate.findOne(query, DiseaseDocument.class);
        return DiseaseMapper.INSTANCE.toModel(disease);
    }

    @Override
    public Disease createDisease(Disease disease) {
        disease.setId(null);
        DiseaseDocument saved = repo.save(DiseaseMapper.INSTANCE.toDocument(disease));
        return DiseaseMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Disease updateDisease(Disease disease) {
        DiseaseDocument saved = repo.save(DiseaseMapper.INSTANCE.toDocument(disease));
        return DiseaseMapper.INSTANCE.toModel(saved);
    }
}
