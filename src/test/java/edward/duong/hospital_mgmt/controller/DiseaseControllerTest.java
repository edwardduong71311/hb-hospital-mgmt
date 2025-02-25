package edward.duong.hospital_mgmt.controller;

import static edward.duong.hospital_mgmt.config.advice.ExceptionAdvice.DEFAULT_ERROR_MESSAGE;
import static edward.duong.hospital_mgmt.domain.exceptions.ExceptionConstant.*;

import com.fasterxml.jackson.core.type.TypeReference;
import edward.duong.hospital_mgmt.IntegrationTestConfig;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseReq;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseRes;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
class DiseaseControllerTest extends IntegrationTestConfig {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String CREATE_DISEASE_URL = "/diseases";
    private static final String GET_DISEASE_URL = "/diseases?page=%d&size=%d";
    private static final String DISEASE_BY_ID_URL = "/diseases/%s";

    private DiseaseReq createDiseaseRequest() {
        return DiseaseReq.builder().name("Test disease " + random.nextDouble()).build();
    }

    private DiseaseRes createDisease(DiseaseReq req) {
        BaseResponse res = this.restTemplate.postForObject(CREATE_DISEASE_URL, req, BaseResponse.class);
        return objectMapper.convertValue(res.getData(), DiseaseRes.class);
    }

    private List<DiseaseRes> getSpecialties(Integer page, Integer size) {
        BaseResponse res =
                this.restTemplate.getForObject(String.format(GET_DISEASE_URL, page, size), BaseResponse.class);
        return objectMapper.convertValue(res.getData(), new TypeReference<List<DiseaseRes>>() {});
    }

    @Test
    @DisplayName("Controller - Create disease success")
    void createDisease_Success() {
        DiseaseReq request = createDiseaseRequest();
        DiseaseRes disease = createDisease(request);

        Assertions.assertNotNull(disease.getId());
        Assertions.assertEquals(request.getName(), disease.getName());
    }

    @Test
    @DisplayName("Controller - Create disease without disease")
    void createDisease_Without_Disease() {
        BaseResponse res = this.restTemplate.postForObject(CREATE_DISEASE_URL, null, BaseResponse.class);
        Assertions.assertEquals(DEFAULT_ERROR_MESSAGE, res.getError());
    }

    @Test
    @DisplayName("Controller - Create disease without name")
    void createDisease_Without_Name() {
        BaseResponse res = this.restTemplate.postForObject(
                CREATE_DISEASE_URL, DiseaseReq.builder().build(), BaseResponse.class);
        Assertions.assertEquals(REQUIRE_DISEASE_NAME, res.getError());
    }

    @Test
    @DisplayName("Controller - Create duplicated disease")
    void createDisease_Duplicated() {
        DiseaseRes disease = createDisease(createDiseaseRequest());

        BaseResponse res = this.restTemplate.postForObject(
                CREATE_DISEASE_URL, DiseaseReq.builder().name(disease.getName()).build(), BaseResponse.class);

        Assertions.assertEquals(DUPLICATE_DISEASE, res.getError());
    }

    @Test
    @DisplayName("Controller - Update disease success")
    void updateDisease_Success() {
        DiseaseReq request = createDiseaseRequest();
        DiseaseRes disease = createDisease(request);

        request.setId(disease.getId());
        request.setName("New name");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                CREATE_DISEASE_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        DiseaseRes saved =
                objectMapper.convertValue(Objects.requireNonNull(res.getBody()).getData(), DiseaseRes.class);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("New name", saved.getName());
    }

    @Test
    @DisplayName("Controller - Update disease does not have Id")
    void updateDisease_Without_Id() {
        DiseaseReq request = createDiseaseRequest();
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                CREATE_DISEASE_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                REQUIRE_DISEASE_ID, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Update disease not existed")
    void updateDisease_NotExisted() {
        DiseaseReq request = createDiseaseRequest();
        request.setId("999999999");

        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                CREATE_DISEASE_URL, HttpMethod.PUT, new HttpEntity<>(request), BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_DISEASE, Objects.requireNonNull(res.getBody()).getError());
    }

    @Test
    @DisplayName("Controller - Get specialties")
    void getSpecialties_Success() {
        createDisease(createDiseaseRequest());
        createDisease(createDiseaseRequest());
        createDisease(createDiseaseRequest());
        createDisease(createDiseaseRequest());

        List<DiseaseRes> specialties = getSpecialties(0, 2);
        Assertions.assertNotNull(specialties);
        Assertions.assertEquals(2, specialties.size());

        List<DiseaseRes> nextSpecialties = getSpecialties(1, 2);
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
    @DisplayName("Controller - Delete disease success")
    void deleteDisease_Success() {
        DiseaseReq request = createDiseaseRequest();
        DiseaseRes disease = createDisease(request);

        ResponseEntity<String> res = this.restTemplate.exchange(
                String.format(DISEASE_BY_ID_URL, disease.getId()), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    @DisplayName("Controller - Delete disease with invalid id")
    void deleteDisease_With_InvalidId() {
        ResponseEntity<BaseResponse> res = this.restTemplate.exchange(
                String.format(DISEASE_BY_ID_URL, "999999999"), HttpMethod.DELETE, null, BaseResponse.class);

        Assertions.assertEquals(
                NOTFOUND_DISEASE, Objects.requireNonNull(res.getBody()).getError());
    }
}
