package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresSpecialistRepo
        extends JpaRepository<SpecialistEntity, Long>, JpaSpecificationExecutor<SpecialistEntity> {}
