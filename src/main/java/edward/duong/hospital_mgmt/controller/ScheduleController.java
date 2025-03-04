package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.mapper.ScheduleMapper;
import edward.duong.hospital_mgmt.controller.models.hospital.AppointmentReq;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleReq;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleRes;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.hospital.Schedule;
import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import edward.duong.hospital_mgmt.service.ScheduleService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleRes> getSchedules(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "hospitalId", required = false) String hospitalId,
            @RequestParam(value = "date", required = false) LocalDateTime date) {
        Pagination pagination = Pagination.builder()
                .page(page < 0 ? 0 : page)
                .size(size < 0 ? 10 : size)
                .build();
        ScheduleCriteria criteria = ScheduleCriteria.builder()
                .id(id)
                .hospitalId(hospitalId)
                .date(date)
                .build();

        return ScheduleMapper.INSTANCE.toResponseList(scheduleService.getSchedules(criteria, pagination));
    }

    @PostMapping
    public ScheduleRes createSchedule(@RequestBody ScheduleReq schedule) {
        return ScheduleMapper.INSTANCE.toResponse(
                scheduleService.createSchedule(ScheduleMapper.INSTANCE.toSchedule(schedule)));
    }

    @PostMapping("/{schedule_id}/appointments")
    public Boolean createAppointment(
            @PathVariable(name = "schedule_id") String scheduleId, @RequestBody AppointmentReq appointment) {
        scheduleService.addAppointment(
                Schedule.builder().id(scheduleId).build(), ScheduleMapper.INSTANCE.toAppointment(appointment));
        return true;
    }
}
