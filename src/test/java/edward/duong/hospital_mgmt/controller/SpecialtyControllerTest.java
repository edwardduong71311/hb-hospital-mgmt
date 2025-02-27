package edward.duong.hospital_mgmt.controller;

import static edward.duong.hospital_mgmt.config.advice.ExceptionAdvice.DEFAULT_ERROR_MESSAGE;
import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.BaseWebTestConfig;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyRes;
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
class SpecialtyControllerTest extends BaseWebTestConfig {
    @Test
    @DisplayName("Controller - Create specialty success")
    void createSpecialty_Success() {
        SpecialtyReq request = createSpecialtyRequest();
        SpecialtyRes specialty = createSpecialty(request);

        Assertions.assertNotNull(specialty.getId());
        Assertions.assertEquals(request.getName(), specialty.getName());
    }

    @Test
    @DisplayName("Controller - Create specialty without specialty")
    void createSpecialty_Without_Specialty() {
        BaseResponse res = this.restTemplate.postForObject(POST_SPECIALTY_URL, null, BaseResponse.class);
        Assertions.assertEquals(DEFAULT_ERROR_MESSAGE, res.getError());
    }

    @Test
    @DisplayName("Controller - Create specialty without name")
    void createSpecialty_Without_Name() {
        BaseResponse res = this.restTemplate.postForObject(
                POST_SPECIALTY_URL,
                SpecialtyReq.builder().description("Description").build(),
                BaseResponse.class);
        Assertions.assertEquals(REQUIRE_SPECIALTY_NAME, res.getError());
    }

    @Test
    @DisplayName("Controller - Create duplicated specialty")
    void createSpecialty_Duplicated() {
        SpecialtyRes specialty = createSpecialty(createSpecialtyRequest());

        BaseResponse res = this.restTemplate.postForObject(
                POST_SPECIALTY_URL,
                SpecialtyReq.builder()
                        .name(specialty.getName())
                        .description(specialty.getDescription())
                        .build(),
                BaseResponse.class);

        Assertions.assertEquals(DUPLICATE_SPECIALTY, res.getError());
    }

    @Test
    @DisplayName("Controller - Update specialty success")
    void updateSpecialty_Success() {
        SpecialtyReq request = createSpecialtyRequest();
        SpecialtyRes specialty = createSpecialty(request);

        request.setId(specialty.getId());
        request.setName("New name");
        request.setDescription("New description");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALTY_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        SpecialtyRes saved =
                objectMapper.convertValue(Objects.requireNonNull(res.getBody()).getData(), SpecialtyRes.class);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("New name", saved.getName());
        Assertions.assertEquals("New description", saved.getDescription());
    }

    @Test
    @DisplayName("Controller - Update specialty does not have Id")
    void updateSpecialty_Without_Id() {
        SpecialtyReq request = createSpecialtyRequest();
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALTY_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                REQUIRE_SPECIALTY_ID, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Update specialty not existed")
    void updateSpecialty_NotExisted() {
        SpecialtyReq request = createSpecialtyRequest();
        request.setId("999999999");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALTY_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_SPECIALTY, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Get specialties")
    void getSpecialties_Success() {
        createSpecialty(createSpecialtyRequest());
        createSpecialty(createSpecialtyRequest());
        createSpecialty(createSpecialtyRequest());
        createSpecialty(createSpecialtyRequest());

        List<SpecialtyRes> specialties = getSpecialties(0, 2);
        Assertions.assertNotNull(specialties);
        Assertions.assertEquals(2, specialties.size());

        List<SpecialtyRes> nextSpecialties = getSpecialties(1, 2);
        Assertions.assertNotNull(nextSpecialties);
        Assertions.assertEquals(2, nextSpecialties.size());

        Assertions.assertNotEquals(
                specialties.getFirst().getId(), nextSpecialties.getFirst().getId());
        Assertions.assertNotEquals(
                specialties.getFirst().getId(), nextSpecialties.getLast().getId());
        Assertions.assertNotEquals(
                specialties.getLast().getId(), nextSpecialties.getFirst().getId());
        Assertions.assertNotEquals(
                specialties.getLast().getId(), nextSpecialties.getLast().getId());
    }

    @Test
    @DisplayName("Controller - Delete specialty success")
    void deleteSpecialty_Success() {
        SpecialtyReq request = createSpecialtyRequest();
        SpecialtyRes specialty = createSpecialty(request);

        ResponseEntity<String> res = this.restTemplate.exchange(
                String.format(SPECIALTY_BY_ID_URL, specialty.getId()), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    @DisplayName("Controller - Delete specialty with invalid id")
    void deleteSpecialty_With_InvalidId() {
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                String.format(SPECIALTY_BY_ID_URL, "999999999"), HttpMethod.DELETE, null, BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_SPECIALTY, Objects.requireNonNull(res.getBody()).getError());
    }
}
