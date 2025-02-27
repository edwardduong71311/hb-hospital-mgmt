package edward.duong.hospital_mgmt.domain.input_ports;

import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import java.util.List;

public interface ScheduleUseCase {
    List<Schedule> getSchedules(ScheduleCriteria criteria, Pagination pagination);

    Schedule createSchedule(Schedule schedule);

    Schedule addAppointment(Schedule schedule, Appointment appointment);
}
