package edward.duong.hospital_mgmt.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import edward.duong.hospital_mgmt.IntegrationTestConfig;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalReq;
import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static edward.duong.hospital_mgmt.config.advice.ExceptionAdvice.DEFAULT_ERROR_MESSAGE;
import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

@Slf4j
class HospitalControllerTest extends IntegrationTestConfig {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String CREATE_HOSPITAL_URL = "/hospitals";
    private static final String GET_HOSPITAL_URL = "/hospitals?page=%d&size=%d";
    private static final String GET_HOSPITAL_BY_ID_URL = "/hospitals/%s";

    private HospitalReq createHospitalRequest() {
        return HospitalReq.builder()
                .name("Test hospital " + random.nextDouble())
                .latitude(100 * random.nextDouble())
                .longitude(200 * random.nextDouble())
                .build();
    }

    private HospitalRes createHospital(HospitalReq hospital) {
        BaseResponse res = this.restTemplate.postForObject(
                CREATE_HOSPITAL_URL, hospital, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), HospitalRes.class);
    }

    private HospitalRes getHospitalById(String id) {
        BaseResponse res = this.restTemplate.getForObject(
                String.format(GET_HOSPITAL_BY_ID_URL, id),
                BaseResponse.class);
        return objectMapper.convertValue(res.getData(), HospitalRes.class);
    }

    private List<HospitalRes> getHospitals(Integer page, Integer size) {
        BaseResponse res = this.restTemplate.getForObject(
                String.format(GET_HOSPITAL_URL, page, size),
                BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<HospitalRes>>() {});
    }

    @Test
    @DisplayName("Controller - Create hospital success")
    void createHospital_Success() {
        HospitalReq request = createHospitalRequest();
        HospitalRes hospital = createHospital(request);
        HospitalRes saved = getHospitalById(hospital.getId());

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(request.getName(), saved.getName());
        Assertions.assertEquals(request.getLatitude(), saved.getLatitude());
        Assertions.assertEquals(request.getLongitude(), saved.getLongitude());
    }

    @Test
    @DisplayName("Controller - Create hospital without hospital")
    void createHospital_Without_Hospital() {
        BaseResponse res = this.restTemplate.postForObject(CREATE_HOSPITAL_URL, null, BaseResponse.class);
        Assertions.assertEquals(DEFAULT_ERROR_MESSAGE, res.getError());
    }

    @Test
    @DisplayName("Controller - Create hospital without name")
    void createHospital_Without_Name() {
        BaseResponse res = this.restTemplate.postForObject(
                CREATE_HOSPITAL_URL,
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
                CREATE_HOSPITAL_URL,
                HospitalReq.builder()
                        .name("Test hospital " + random.nextDouble())
                        .build(),
                BaseResponse.class);

        Assertions.assertEquals(REQUIRE_HOSPITAL_LOCATION, res.getError());
    }

    @Test
    @DisplayName("Controller - Create duplicated hospital")
    void createHospital_Duplicated() {
        HospitalRes hospital = createHospital(createHospitalRequest());

        BaseResponse res = this.restTemplate.postForObject(
                CREATE_HOSPITAL_URL,
                HospitalReq.builder()
                        .name(hospital.getName())
                        .latitude(hospital.getLatitude())
                        .longitude(hospital.getLongitude())
                        .build(),
                BaseResponse.class);

        Assertions.assertEquals(DUPLICATE_HOSPITAL, res.getError());
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

        this.restTemplate.put(CREATE_HOSPITAL_URL, request, BaseResponse.class);
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
                CREATE_HOSPITAL_URL,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponse.class);

        Assertions.assertEquals(REQUIRE_HOSPITAL_ID, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Update hospital not existed")
    void updateHospital_NotExisted() {
        HospitalReq request = createHospitalRequest();
        request.setId("999999999");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                CREATE_HOSPITAL_URL,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponse.class);

        Assertions.assertEquals(NOTFOUND_HOSPITAL, Objects.requireNonNull(res.getBody()).getError());
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

        Assertions.assertNotEquals(hospitals.getFirst().getId(), nextHospitals.getFirst().getId());
        Assertions.assertNotEquals(hospitals.getFirst().getId(), nextHospitals.getLast().getId());
        Assertions.assertNotEquals(hospitals.getLast().getId(), nextHospitals.getFirst().getId());
        Assertions.assertNotEquals(hospitals.getLast().getId(), nextHospitals.getLast().getId());
    }

}
