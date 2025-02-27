package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresSpecialtyRepo
        extends JpaRepository<SpecialtyEntity, Long>, JpaSpecificationExecutor<SpecialtyEntity> {}
