package edward.duong.hospital_mgmt.controller;

import static edward.duong.hospital_mgmt.config.advice.ExceptionAdvice.DEFAULT_ERROR_MESSAGE;
import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.BaseWebTestConfig;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.hospital.HospitalReq;
import edward.duong.hospital_mgmt.controller.models.hospital.HospitalRes;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import edward.duong.hospital_mgmt.domain.models.hospital.HospitalStatus;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
class HospitalControllerTest extends BaseWebTestConfig {
    @Test
    @DisplayName("Controller - Create hospital success")
    void createHospital_Success() {
        HospitalReq request = createHospitalRequest();
        HospitalRes hospital = createHospital(request);
        HospitalRes saved = getHospitalById(hospital.getId());

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(request.getName(), saved.getName());
        Assertions.assertEquals(HospitalStatus.ACTIVE.name(), saved.getStatus());
        Assertions.assertEquals(request.getLatitude(), saved.getLatitude());
        Assertions.assertEquals(request.getLongitude(), saved.getLongitude());
    }

    @Test
    @DisplayName("Controller - Create hospital with specialists success")
    void createHospital_With_Specialists_Success() {
        HospitalReq request = createHospitalRequest();

        SpecialistReq s1 = createSpecialistRequest();
        SpecialistReq s2 = createSpecialistRequest();
        SpecialistRes spec1 = createSpecialist(s1);
        SpecialistRes spec2 = createSpecialist(s2);

        s1.setId(spec1.getId());
        s2.setId(spec2.getId());
        request.setSpecialists(List.of(s1, s2));

        HospitalRes hospital = createHospital(request);
        HospitalRes saved = getHospitalById(hospital.getId());

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(request.getName(), saved.getName());
        Assertions.assertEquals(HospitalStatus.ACTIVE.name(), saved.getStatus());
        Assertions.assertEquals(request.getLatitude(), saved.getLatitude());
        Assertions.assertEquals(request.getLongitude(), saved.getLongitude());
        Assertions.assertEquals(2, saved.getSpecialists().size());

        // Update again to empty
        request.setId(saved.getId());
        request.setSpecialists(null);
        ResponseEntity<BaseResponse> updated = this.restTemplate.exchange(
                POST_HOSPITAL_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);
        HospitalRes updatedHospital = objectMapper.convertValue(
                Objects.requireNonNull(updated.getBody()).getData(), HospitalRes.class);
        Assertions.assertEquals(0, updatedHospital.getSpecialists().size());
    }

    @Test
    @DisplayName("Controller - Create hospital without hospital")
    void createHospital_Without_Hospital() {
        BaseResponse res = this.restTemplate.postForObject(POST_HOSPITAL_URL, null, BaseResponse.class);
        Assertions.assertEquals(DEFAULT_ERROR_MESSAGE, res.getError());
    }

    @Test
    @DisplayName("Controller - Create hospital without name")
    void createHospital_Without_Name() {
        BaseResponse res = this.restTemplate.postForObject(
                POST_HOSPITAL_URL,
                HospitalReq.builder()
                        .latitude(100 * random.nextDouble())
                        .longitude(200 * random.nextDouble())
                        .build(),
                BaseResponse.class);

        Assertions.assertEquals(REQUIRE_HOSPITAL_NAME, res.getError());
    }

    @Test
    @DisplayName("Controller - Create hospital without location")
    void createHospital_Without_Location() {
        BaseResponse res = this.restTemplate.postForObject(
                POST_HOSPITAL_URL,
                HospitalReq.builder()
                        .name("Test hospital " + random.nextDouble())
                        .build(),
                BaseResponse.class);

        Assertions.assertEquals(REQUIRE_HOSPITAL_LOCATION, res.getError());
    }

    @Test
    @DisplayName("Controller - Update hospital success")
    void updateHospital_Success() {
        HospitalReq request = createHospitalRequest();
        HospitalRes hospital = createHospital(request);

        request.setId(hospital.getId());
        request.setName("New hospital name");
        request.setLatitude(100.100d);
        request.setLongitude(200.200d);

        this.restTemplate.put(POST_HOSPITAL_URL, request, BaseResponse.class);
        HospitalRes saved = getHospitalById(hospital.getId());

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("New hospital name", saved.getName());
        Assertions.assertEquals(100.100d, saved.getLatitude());
        Assertions.assertEquals(200.200d, saved.getLongitude());
    }

    @Test
    @DisplayName("Controller - Update hospital does not have Id")
    void updateHospital_Without_Id() {
        HospitalReq request = createHospitalRequest();
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_HOSPITAL_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                REQUIRE_HOSPITAL_ID, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Update hospital not existed")
    void updateHospital_NotExisted() {
        HospitalReq request = createHospitalRequest();
        request.setId("999999999");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_HOSPITAL_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_HOSPITAL, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Get hospitals")
    void getHospitals_Success() {
        createHospital(createHospitalRequest());
        createHospital(createHospitalRequest());
        createHospital(createHospitalRequest());
        createHospital(createHospitalRequest());

        List<HospitalRes> hospitals = getHospitals(0, 2);
        Assertions.assertNotNull(hospitals);
        Assertions.assertEquals(2, hospitals.size());

        List<HospitalRes> nextHospitals = getHospitals(1, 2);
        Assertions.assertNotNull(hospitals);
        Assertions.assertEquals(2, hospitals.size());

        Assertions.assertNotEquals(
                hospitals.getFirst().getId(), nextHospitals.getFirst().getId());
        Assertions.assertNotEquals(
                hospitals.getFirst().getId(), nextHospitals.getLast().getId());
        Assertions.assertNotEquals(
                hospitals.getLast().getId(), nextHospitals.getFirst().getId());
        Assertions.assertNotEquals(
                hospitals.getLast().getId(), nextHospitals.getLast().getId());
    }

    @Test
    @DisplayName("Controller - Delete hospital success")
    void deleteHospital_Success() {
        HospitalReq request = createHospitalRequest();
        HospitalRes hospital = createHospital(request);

        ResponseEntity<String> res = this.restTemplate.exchange(
                String.format(GET_HOSPITAL_BY_ID_URL, hospital.getId()), HttpMethod.DELETE, null, String.class);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

        HospitalRes saved = getHospitalById(hospital.getId());
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(HospitalStatus.INACTIVE.name(), saved.getStatus());
    }

    @Test
    @DisplayName("Controller - Delete hospital with invalid id")
    void deleteHospital_With_InvalidId() {
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                String.format(GET_HOSPITAL_BY_ID_URL, "999999999"), HttpMethod.DELETE, null, BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_HOSPITAL, Objects.requireNonNull(res.getBody()).getError());
    }
}
