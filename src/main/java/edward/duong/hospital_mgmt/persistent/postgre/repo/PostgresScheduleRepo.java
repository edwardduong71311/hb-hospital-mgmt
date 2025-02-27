package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresScheduleRepo
        extends JpaRepository<ScheduleEntity, Long>, JpaSpecificationExecutor<ScheduleEntity> {}
