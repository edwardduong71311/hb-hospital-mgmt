package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.domain.input_ports.ScheduleUseCase;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleUseCase useCase;

    public ScheduleService(ScheduleUseCase useCase) {
        this.useCase = useCase;
    }

    public List<Schedule> getSchedules(ScheduleCriteria criteria, Pagination pagination) {
        return useCase.getSchedules(criteria, pagination);
    }

    public Schedule createSchedule(Schedule schedule) {
        return useCase.createSchedule(schedule);
    }

    public Schedule addAppointment(Schedule schedule, Appointment appointment) {
        return useCase.addAppointment(schedule, appointment);
    }
}
