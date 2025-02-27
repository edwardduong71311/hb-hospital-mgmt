package edward.duong.hospital_mgmt.persistent.postgre.repo;

import edward.duong.hospital_mgmt.persistent.postgre.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresAppointmentRepo extends JpaRepository<AppointmentEntity, Long> {}
