package edward.duong.hospital_mgmt.persistent.mongo;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.persistent.mongo.items.DiseaseDocument;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.mongodb.repository.MongoRepository;

@ConditionalOnBean({MongoConfig.class})
public interface MongoDiseaseRepo extends MongoRepository<DiseaseDocument, String> {}
