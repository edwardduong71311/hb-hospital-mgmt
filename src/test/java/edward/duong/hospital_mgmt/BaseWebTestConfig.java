package edward.duong.hospital_mgmt;

import com.fasterxml.jackson.core.type.TypeReference;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseReq;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseRes;
import edward.duong.hospital_mgmt.controller.models.hospital.*;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyRes;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {DisableSecurityConfig.class})
public abstract class BaseWebTestConfig extends IntegrationTestConfig {
    @Autowired
    protected TestRestTemplate restTemplate;

    protected static final String POST_DISEASE_URL = "/diseases";
    protected static final String GET_DISEASE_URL = "/diseases?page=%d&size=%d";
    protected static final String DISEASE_BY_ID_URL = "/diseases/%s";

    protected static final String POST_SPECIALTY_URL = "/specialties";
    protected static final String GET_SPECIALTY_URL = "/specialties?page=%d&size=%d";
    protected static final String SPECIALTY_BY_ID_URL = "/specialties/%s";

    protected static final String POST_SPECIALIST_URL = "/specialists";
    protected static final String GET_SPECIALIST_URL = "/specialists?page=%d&size=%d";
    protected static final String SPECIALIST_BY_ID_URL = "/specialists/%s";

    protected static final String POST_HOSPITAL_URL = "/hospitals";
    protected static final String GET_HOSPITAL_URL = "/hospitals?page=%d&size=%d";
    protected static final String GET_HOSPITAL_BY_ID_URL = "/hospitals/%s";

    protected static final String POST_SCHEDULE_URL = "/schedules";
    protected static final String GET_SCHEDULE_URL = "/schedules?page=%d&size=%d&";
    protected static final String POST_APPOINTMENT_URL = "/schedules/%s/appointments";

    protected DiseaseReq createDiseaseRequest() {
        return DiseaseReq.builder().name("Test disease " + random.nextDouble()).build();
    }

    protected DiseaseRes createDisease(DiseaseReq req) {
        BaseResponse res = this.restTemplate.postForObject(POST_DISEASE_URL, req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), DiseaseRes.class);
    }

    protected List<DiseaseRes> getDiseases(Integer page, Integer size) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_DISEASE_URL, page, size), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<DiseaseRes>>() {});
    }

    protected SpecialtyReq createSpecialtyRequest() {
        return SpecialtyReq.builder()
                .name("Test specialty " + random.nextDouble())
                .description("Description " + random.nextDouble())
                .build();
    }

    protected SpecialtyRes createSpecialty(SpecialtyReq req) {
        BaseResponse res = this.restTemplate.postForObject(POST_SPECIALTY_URL, req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), SpecialtyRes.class);
    }

    protected List<SpecialtyRes> getSpecialties(Integer page, Integer size) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_SPECIALTY_URL, page, size), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<SpecialtyRes>>() {});
    }

    protected SpecialistReq createSpecialistRequest() {
        return SpecialistReq.builder()
                .name("Test specialist " + random.nextDouble())
                .build();
    }

    protected SpecialistRes createSpecialist(SpecialistReq req) {
        BaseResponse res = this.restTemplate.postForObject(POST_SPECIALIST_URL, req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), SpecialistRes.class);
    }

    protected List<SpecialistRes> getSpecialists(Integer page, Integer size) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_SPECIALIST_URL, page, size), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<SpecialistRes>>() {});
    }

    protected HospitalReq createHospitalRequest() {
        return HospitalReq.builder()
                .name("Test hospital " + random.nextDouble())
                .latitude(100 * random.nextDouble())
                .longitude(200 * random.nextDouble())
                .build();
    }

    protected HospitalRes createHospital(HospitalReq hospital) {
        BaseResponse res = this.restTemplate.postForObject(POST_HOSPITAL_URL, hospital, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), HospitalRes.class);
    }

    protected HospitalRes getHospitalById(String id) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_HOSPITAL_BY_ID_URL, id), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), HospitalRes.class);
    }

    protected List<HospitalRes> getHospitals(Integer page, Integer size) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_HOSPITAL_URL, page, size), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<HospitalRes>>() {});
    }

    protected ScheduleReq createScheduleRequest(String hospitalId, String specialistId) {
        return ScheduleReq.builder()
                .date(LocalDateTime.now())
                .fromTime(LocalTime.now())
                .toTime(LocalTime.now().plusHours(10))
                .hospitalId(hospitalId)
                .specialistId(specialistId)
                .build();
    }

    protected ScheduleRes createSchedule(ScheduleReq req) {
        BaseResponse res = this.restTemplate.postForObject(POST_SCHEDULE_URL, req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), ScheduleRes.class);
    }

    protected List<ScheduleRes> getSchedules(
            Integer page, Integer size, String id, String hospitalId, LocalDateTime date) {
        String url = String.format(GET_SCHEDULE_URL, page, size);
        if (Objects.nonNull(id)) {
            url += String.format("id=%s&", id);
        }
        if (Objects.nonNull(hospitalId)) {
            url += String.format("hospitalId=%s&", hospitalId);
        }
        if (Objects.nonNull(date)) {
            url += String.format("date=%s&", date);
        }

        BaseResponse res = this.restTemplate.getForObject(url, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<>() {});
    }

    protected Boolean createAppointment(String scheduleId, AppointmentReq req) {
        BaseResponse res = this.restTemplate.postForObject(
                String.format(POST_APPOINTMENT_URL, scheduleId), req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), Boolean.class);
    }
}
