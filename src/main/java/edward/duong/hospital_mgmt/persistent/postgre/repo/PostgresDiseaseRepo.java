package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresDiseaseRepo
        extends JpaRepository<DiseaseEntity, Long>, JpaSpecificationExecutor<DiseaseEntity> {}
