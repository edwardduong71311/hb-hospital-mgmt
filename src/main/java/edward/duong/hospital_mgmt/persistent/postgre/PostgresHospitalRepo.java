package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.jpa.repository.JpaRepository;

@ConditionalOnBean({PostgresConfig.class})
public interface PostgresHospitalRepo extends JpaRepository<HospitalEntity, Long> {}
