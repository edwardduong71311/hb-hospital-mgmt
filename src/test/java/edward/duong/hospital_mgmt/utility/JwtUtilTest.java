package edward.duong.hospital_mgmt.utility;

import edward.duong.hospital_mgmt.BaseUnitTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JwtUtilTest extends BaseUnitTestConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("Utility - Create token success")
    public void createTokenSuccess() {
        String userName =  "UserName";

        String token = jwtUtil.generateToken(userName);
        boolean isValid = jwtUtil.validateToken(token, userName);
        String extractedUserName = jwtUtil.extractUsername(token);

        Assertions.assertTrue(isValid);
        Assertions.assertEquals(userName, extractedUserName);
    }
}
