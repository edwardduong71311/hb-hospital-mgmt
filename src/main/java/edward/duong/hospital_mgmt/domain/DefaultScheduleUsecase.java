package edward.duong.hospital_mgmt.domain;

import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.NOTFOUND_SCHEDULE;

import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.domain.input_ports.ScheduleUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import edward.duong.hospital_mgmt.domain.output_ports.SchedulePersistent;
import java.util.List;

public class DefaultScheduleUsecase implements ScheduleUseCase {
    private final SchedulePersistent persistent;
    private final HospitalUseCase hospitalUseCase;

    public DefaultScheduleUsecase(SchedulePersistent persistent, HospitalUseCase hospitalUseCase) {
        this.persistent = persistent;
        this.hospitalUseCase = hospitalUseCase;
    }

    @Override
    public List<Schedule> getSchedules(ScheduleCriteria criteria, Pagination pagination) {
        return persistent.getSchedules(criteria, pagination);
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {
        hospitalUseCase.isHospitalExist(schedule.getHospital().getId());
        return persistent.createSchedule(schedule);
    }

    @Override
    public Schedule addAppointment(Schedule schedule, Appointment appointment) {
        boolean invalidSchedule = getSchedules(
                        ScheduleCriteria.builder().id(schedule.getId()).build(),
                        Pagination.builder().page(0).size(1).build())
                .isEmpty();
        if (invalidSchedule) {
            throw new IllegalArgumentException(NOTFOUND_SCHEDULE);
        }

        return persistent.addAppointment(schedule, appointment);
    }
}
