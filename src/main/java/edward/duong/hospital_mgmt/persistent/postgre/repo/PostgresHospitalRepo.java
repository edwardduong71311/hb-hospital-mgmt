package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresHospitalRepo
        extends JpaRepository<HospitalEntity, Long>, JpaSpecificationExecutor<HospitalEntity> {}
