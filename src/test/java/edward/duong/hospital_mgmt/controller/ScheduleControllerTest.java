package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.BaseWebTestConfig;
import edward.duong.hospital_mgmt.controller.models.hospital.AppointmentReq;
import edward.duong.hospital_mgmt.controller.models.hospital.HospitalRes;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleReq;
import edward.duong.hospital_mgmt.controller.models.hospital.ScheduleRes;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ScheduleControllerTest extends BaseWebTestConfig {
    @Test
    @DisplayName("Controller - Create schedule success and then add appointment")
    void createSchedule_Success_And_Appointment() {
        HospitalRes hospital = createHospital(createHospitalRequest());
        SpecialistRes specialist = createSpecialist(createSpecialistRequest());

        ScheduleReq request = createScheduleRequest(hospital.getId(), specialist.getId());
        ScheduleRes saved = createSchedule(request);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(request.getDate(), saved.getDate());
        Assertions.assertEquals(request.getFromTime(), saved.getFromTime());
        Assertions.assertEquals(request.getToTime(), saved.getToTime());

        // Get schedules
        List<ScheduleRes> res = getSchedules(0, 10, null, null, null);
        Assertions.assertEquals(1, res.size());

        // Add appointment
        Boolean isSaved = createAppointment(
                saved.getId(), AppointmentReq.builder().patient("Patient Name").build());
        Assertions.assertTrue(isSaved);
    }
}
