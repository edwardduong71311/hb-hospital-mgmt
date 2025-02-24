package edward.duong.hospital_mgmt.security;

import edward.duong.hospital_mgmt.BaseWebTestConfig;
import edward.duong.hospital_mgmt.controller.models.AuthResponse;
import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

class SecurityTest extends BaseWebTestConfig {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Security - Forbidden if does not have token")
    void getForbidden() {
        ResponseEntity<BaseResponse> res =
                this.restTemplate.getForEntity(String.format("/hospitals?page=%d&size=%d", 0, 10), BaseResponse.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    @DisplayName("Security - Acquire token and access data")
    void getSuccess() {
        BaseResponse res = this.restTemplate.postForObject(
                String.format("/auth/login?email=%s&password=%s", "user", "pass"), null, BaseResponse.class);
        AuthResponse auth = objectMapper.convertValue(res.getData(), AuthResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", auth.getToken()));
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<BaseResponse> data = restTemplate.exchange(
                String.format("/hospitals?page=%d&size=%d", 0, 10),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BaseResponse.class);
        Assertions.assertEquals(HttpStatus.OK, data.getStatusCode());
    }
}
