package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.hospital.AppointmentReq;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleReq;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleRes;
import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "patient", target = "patient")
    Appointment toAppointment(AppointmentReq req);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "fromTime", target = "fromTime")
    @Mapping(source = "toTime", target = "toTime")
    @Mapping(source = "specialistId", target = "specialist.id")
    @Mapping(source = "hospitalId", target = "hospital.id")
    Schedule toSchedule(ScheduleReq req);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "fromTime", target = "fromTime")
    @Mapping(source = "toTime", target = "toTime")
    @Mapping(source = "hospital", target = "hospital")
    @Mapping(source = "specialist", target = "specialist")
    @Mapping(source = "appointment", target = "appointment")
    ScheduleRes toResponse(Schedule schedule);

    List<ScheduleRes> toResponseList(List<Schedule> hospital);
}
