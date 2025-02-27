package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.hospital.Appointment;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.persistent.postgre.entity.AppointmentEntity;
import edward.duong.hospital_mgmt.persistent.postgre.entity.ScheduleEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "fromTime", target = "fromTime")
    @Mapping(source = "toTime", target = "toTime")
    Schedule toModel(ScheduleEntity a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "fromTime", target = "fromTime")
    @Mapping(source = "toTime", target = "toTime")
    @Mapping(target = "hospital", ignore = true)
    @Mapping(target = "specialist", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    ScheduleEntity toEntity(Schedule a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "patient", target = "patient")
    Appointment toModel(AppointmentEntity a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "patient", target = "patient")
    AppointmentEntity toEntity(Appointment a);

    List<Schedule> entityToModels(List<ScheduleEntity> a);
}
