package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import edward.duong.hospital_mgmt.persistent.mapper.HospitalMapper;
import edward.duong.hospital_mgmt.persistent.mapper.ScheduleMapper;
import edward.duong.hospital_mgmt.persistent.mapper.SpecialistMapper;
import edward.duong.hospital_mgmt.persistent.postgre.entity.AppointmentEntity;
import edward.duong.hospital_mgmt.persistent.postgre.entity.ScheduleEntity;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresAppointmentRepo;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresScheduleRepo;
import edward.duong.hospital_mgmt.persistent.postgre.specification.ScheduleSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SchedulePersistent implements edward.duong.hospital_mgmt.domain.output_ports.SchedulePersistent {
    private final PostgresScheduleRepo repo;
    private final PostgresAppointmentRepo appointmentRepo;

    public SchedulePersistent(PostgresScheduleRepo repo, PostgresAppointmentRepo appointmentRepo) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public List<Schedule> getSchedules(ScheduleCriteria criteria, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Specification<ScheduleEntity> spec = ScheduleSpecification.filterByCriteria(criteria);
        return ScheduleMapper.INSTANCE.entityToModels(
                repo.findAll(spec, pageable).get().toList());
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {
        ScheduleEntity entity = ScheduleMapper.INSTANCE.toEntity(schedule);
        entity.setHospital(HospitalMapper.INSTANCE.toUpdateEntity(schedule.getHospital()));
        entity.setSpecialist(SpecialistMapper.INSTANCE.toUpdateEntity(schedule.getSpecialist()));
        return ScheduleMapper.INSTANCE.toModel(repo.save(entity));
    }

    @Override
    public Schedule addAppointment(Schedule schedule, Appointment appointment) {
        ScheduleEntity scheduleEntity =
                repo.findById(Long.valueOf(schedule.getId())).orElseThrow();

        AppointmentEntity entity = ScheduleMapper.INSTANCE.toEntity(appointment);
        AppointmentEntity saved = appointmentRepo.save(entity);

        scheduleEntity.setAppointment(saved);
        return ScheduleMapper.INSTANCE.toModel(repo.save(scheduleEntity));
    }
}
