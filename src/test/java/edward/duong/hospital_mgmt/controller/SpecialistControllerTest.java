package edward.duong.hospital_mgmt.controller;

import static edward.duong.hospital_mgmt.config.advice.ExceptionAdvice.DEFAULT_ERROR_MESSAGE;
import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import edward.duong.hospital_mgmt.BaseWebTestConfig;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
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
class SpecialistControllerTest extends BaseWebTestConfig {
    @Test
    @DisplayName("Controller - Create specialist success")
    void createSpecialist_Success() {
        SpecialistReq request = createSpecialistRequest();
        SpecialistRes specialist = createSpecialist(request);

        Assertions.assertNotNull(specialist.getId());
        Assertions.assertEquals(request.getName(), specialist.getName());
    }

    @Test
    @DisplayName("Controller - Create specialist with specialties success")
    void createSpecialist_With_Specialties_Success() {
        SpecialistReq request = createSpecialistRequest();

        SpecialtyReq s1 = createSpecialtyRequest();
        SpecialtyReq s2 = createSpecialtyRequest();
        SpecialtyRes spec1 = createSpecialty(s1);
        SpecialtyRes spec2 = createSpecialty(s2);

        s1.setId(spec1.getId());
        s2.setId(spec2.getId());
        request.setSpecialties(List.of(s1, s2));

        SpecialistRes specialist = createSpecialist(request);

        Assertions.assertNotNull(specialist.getId());
        Assertions.assertEquals(request.getName(), specialist.getName());
        Assertions.assertEquals(2, specialist.getSpecialties().size());
        Assertions.assertTrue(List.of(spec1.getId(), spec2.getId())
                .contains(specialist.getSpecialties().getFirst().getId()));
        Assertions.assertTrue(List.of(spec1.getId(), spec2.getId())
                .contains(specialist.getSpecialties().getLast().getId()));

        // Update again to empty
        request.setId(specialist.getId());
        request.setSpecialties(null);
        ResponseEntity<BaseResponse> updated = this.restTemplate.exchange(
                POST_SPECIALIST_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);
        SpecialistRes updatedSpecialist = objectMapper.convertValue(
                Objects.requireNonNull(updated.getBody()).getData(), SpecialistRes.class);
        Assertions.assertEquals(0, updatedSpecialist.getSpecialties().size());
    }

    @Test
    @DisplayName("Controller - Create specialist without specialist")
    void createSpecialist_Without_Specialist() {
        BaseResponse res = this.restTemplate.postForObject(POST_SPECIALIST_URL, null, BaseResponse.class);
        Assertions.assertEquals(DEFAULT_ERROR_MESSAGE, res.getError());
    }

    @Test
    @DisplayName("Controller - Create specialist without name")
    void createSpecialist_Without_Name() {
        BaseResponse res = this.restTemplate.postForObject(
                POST_SPECIALIST_URL, SpecialistReq.builder().build(), BaseResponse.class);
        Assertions.assertEquals(REQUIRE_SPECIALIST_NAME, res.getError());
    }

    @Test
    @DisplayName("Controller - Create duplicated specialist")
    void createSpecialist_Duplicated() {
        SpecialistRes specialist = createSpecialist(createSpecialistRequest());

        BaseResponse res = this.restTemplate.postForObject(
                POST_SPECIALIST_URL,
                SpecialistReq.builder().name(specialist.getName()).build(),
                BaseResponse.class);

        Assertions.assertEquals(DUPLICATE_SPECIALIST, res.getError());
    }

    @Test
    @DisplayName("Controller - Update specialist success")
    void updateSpecialist_Success() {
        SpecialistReq request = createSpecialistRequest();
        SpecialistRes specialist = createSpecialist(request);

        request.setId(specialist.getId());
        request.setName("New name");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALIST_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        SpecialistRes saved =
                objectMapper.convertValue(Objects.requireNonNull(res.getBody()).getData(), SpecialistRes.class);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("New name", saved.getName());
    }

    @Test
    @DisplayName("Controller - Update specialist does not have Id")
    void updateSpecialist_Without_Id() {
        SpecialistReq request = createSpecialistRequest();
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALIST_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                REQUIRE_SPECIALIST_ID, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Update specialist not existed")
    void updateSpecialist_NotExisted() {
        SpecialistReq request = createSpecialistRequest();
        request.setId("999999999");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                POST_SPECIALIST_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_SPECIALIST, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Get specialties")
    void getSpecialties_Success() {
        createSpecialist(createSpecialistRequest());
        createSpecialist(createSpecialistRequest());
        createSpecialist(createSpecialistRequest());
        createSpecialist(createSpecialistRequest());

        List<SpecialistRes> specialties = getSpecialists(0, 2);
        Assertions.assertNotNull(specialties);
        Assertions.assertEquals(2, specialties.size());

        List<SpecialistRes> nextSpecialties = getSpecialists(1, 2);
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
    @DisplayName("Controller - Delete specialist success")
    void deleteSpecialist_Success() {
        SpecialistReq request = createSpecialistRequest();
        SpecialistRes specialist = createSpecialist(request);

        ResponseEntity<String> res = this.restTemplate.exchange(
                String.format(SPECIALIST_BY_ID_URL, specialist.getId()), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    @DisplayName("Controller - Delete specialist with invalid id")
    void deleteSpecialist_With_InvalidId() {
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                String.format(SPECIALIST_BY_ID_URL, "999999999"), HttpMethod.DELETE, null, BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_SPECIALIST, Objects.requireNonNull(res.getBody()).getError());
    }
}
